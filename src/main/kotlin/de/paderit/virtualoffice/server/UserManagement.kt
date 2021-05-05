package de.paderit.virtualoffice.server

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.userManagement(userService: UserService, tokenService: JwtTokenService){
    route("/login"){
        post{
            val loginRequest = call.receive<LoginRequest>()
            if(userService.authenticateUser(loginRequest.username, loginRequest.password)){
                call.respond(tokenService.generateToken(loginRequest.username))
            } else {
                call.response.status(HttpStatusCode.Unauthorized)
            }
        }
    }
}

data class LoginRequest(val username: String, val password: String)