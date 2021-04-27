package de.paderit.virtualoffice.server

import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.features.*
import io.ktor.gson.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.util.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@KtorExperimentalAPI
@InternalAPI
@Suppress("unused") // Referenced in application.conf
fun Application.module() {
    val authProvider: AuthProvider = AuthProvider()

    install(ContentNegotiation) {
        gson {
            setPrettyPrinting()
        }
    }
    install(Authentication) {
        form("auth-form") {
            userParamName = "username"
            passwordParamName = "password"
            validate { credentials -> authProvider.authenticate(credentials.name, credentials.password) }
        }
    }
    install(Routing){
        userManagement()
    }
}