package de.paderit.virtualoffice.server

import io.ktor.application.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.util.*

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
                call.respond(LoginResponse(true, "stuff"))
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