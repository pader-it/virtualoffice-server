package de.paderit.virtualoffice.server

import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.features.*
import io.ktor.locations.*
import io.ktor.routing.*
import io.ktor.util.*
import org.slf4j.event.Level
import java.util.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@KtorExperimentalAPI
val userTable = UserHashedTableAuth(
    getDigestFunction("SHA-256") { "ktor${it.length}" },
    table = mapOf(
        "test" to Base64.getDecoder().decode("GSjkHCHGAxTTbnkEDBbVYd+PUFRlcWiumc4+MWE9Rvw=") // sha256 for "test"
    )
)

@KtorExperimentalAPI
@InternalAPI
@Suppress("unused") // Referenced in application.conf
fun Application.module() {
    install(AutoHeadResponse)
    install(DefaultHeaders)
    install(CallLogging){
        level = Level.INFO
    }
    install(Locations)
    install(Authentication) {
        basic("hashAuth") {
            realm = "ktor"
            validate { credentials -> userTable.authenticate(credentials) }
        }
    }
    install(Routing) {
        userManagement()
    }

}