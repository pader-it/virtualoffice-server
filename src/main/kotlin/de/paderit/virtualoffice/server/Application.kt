package de.paderit.virtualoffice.server

import com.auth0.jwt.algorithms.Algorithm
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import io.ktor.features.*
import io.ktor.gson.*
import io.ktor.routing.*
import io.ktor.util.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@KtorExperimentalAPI
@Suppress("unused") // Referenced in application.conf
fun Application.module() {
    environment.log.info("Starting Virtual Office server")

    val myRealm = environment.config.property("jwt.realm").getString()
    val secret = environment.config.property("jwt.secret").getString()
    val expiration = environment.config.property("jwt.expiration_ms").getString().toLong()
    val issuer = environment.config.property("jwt.domain").getString()
    val authAlgorithm = Algorithm.HMAC512(secret)

    val userService = UserService()
    val tokenService = JwtTokenService(authAlgorithm, expiration, issuer)
    val officeManager = OfficeManager()

    install(CallLogging)
    install(ContentNegotiation) {
        gson {
            setPrettyPrinting()
        }
    }
    install(Authentication) {
        jwt("auth-jwt") {
            realm = myRealm
            verifier(tokenService.buildVerifier(authAlgorithm, issuer))
            validate { validateUser(it, userService) }
        }
    }
    install(Routing) {
        userManagement(userService, tokenService)
        defaultapi(officeManager)
    }
}

fun validateUser(jwtCredential: JWTCredential, userService: UserService): Principal? {
    return if(userService.doesUserExist(jwtCredential.payload.getClaim("login").asString())){
        JWTPrincipal(jwtCredential.payload)
    } else {
        null
    }
}