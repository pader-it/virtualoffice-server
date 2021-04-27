package de.paderit.virtualoffice.server

import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.features.*
import io.ktor.gson.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.sessions.*
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
    //install(Authentication) {}
    install(Routing) {
        userManagement(authProvider)
    }
}