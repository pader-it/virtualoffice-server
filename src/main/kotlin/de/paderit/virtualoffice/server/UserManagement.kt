package de.paderit.virtualoffice.server

import io.ktor.application.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.util.*
import java.security.MessageDigest

@InternalAPI
fun Route.userManagement(authProvider: AuthProvider){
    route("/"){
        get{
            call.respondText("Hello World!")
        }
    }
    route("/login"){
        post{
            val loginRequest = call.receive<LoginRequest>()
            val principal = authProvider.authenticate(loginRequest.username, loginRequest.password)
            if(principal == null){
                call.respond(LoginResponse(false, ""))
            } else {
                val md = MessageDigest.getInstance("SHA-1")
                val token = List(20) {
                    (('a'..'z') + ('A'..'Z') + ('0'..'9')).random()
                }.joinToString("")
                authProvider.addSession(token, principal)
                call.respond(LoginResponse(true, token))
            }
        }
    }
}

data class LoginRequest(
    val username: String,
    val password: String
)

data class LoginResponse(
    val ok: Boolean,
    val token: String
)