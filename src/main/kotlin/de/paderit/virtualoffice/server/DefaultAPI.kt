package de.paderit.virtualoffice.server

import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.util.*

@InternalAPI
fun Route.defaultapi(authProvider: AuthProvider) {
    route("/userinfo") {
        get {
            val session = call.receive<SessionToken>()

            val principal = if(session != null && authProvider.authenticate(session.token) != null){
                authProvider.authenticate(session.token)
            } else
                null

            if(principal != null){
                call.respondText("authentication as "+principal.name)
            } else {
                call.respondText("authentication failed")
            }
        }
    }
}

data class SessionToken(
    val token: String
)