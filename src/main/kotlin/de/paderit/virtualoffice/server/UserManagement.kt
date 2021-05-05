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
    route("/register"){
        post{
            val regRequest = call.receive<RegistrationRequest>()
            if(!userService.doesUserExist(regRequest.username)){
                userService.registerUser(regRequest.username, regRequest.password)
                call.response.status(HttpStatusCode.OK)
            } else{
                call.response.status(HttpStatusCode.Conflict)
            }
        }
    }
}

data class LoginRequest(val username: String, val password: String)
data class RegistrationRequest(val username: String, val password: String)