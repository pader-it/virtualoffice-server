package de.paderit.virtualoffice.server

import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.util.*

@InternalAPI
fun Route.userManagement(){
    route("/"){
        get("/") {
            call.respondText("Hello World!\n")
        }
    }
    route("/login"){
        authenticate("hashAuth") {
            post {
                call.application.environment.log.info("Login request")
                call.respond(HttpStatusCode.OK, "OK")
            }
        }
    }

}