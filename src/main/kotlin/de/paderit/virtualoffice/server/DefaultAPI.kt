package de.paderit.virtualoffice.server

import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.util.*

fun Route.defaultapi(officeManager: OfficeManager) {
    authenticate("auth-jwt"){
        route("/hello"){
            get {
                call.respondText("Hello World!")
            }
        }
        route("/userinfo") {
            get {
                call.respondText(call.authentication.principal.toString())
            }
        }
    }
    /*
    route("/office"){
        get{
            val session = call.receive<SessionToken>()
            val principal = if(authProvider.authenticate(session.token) != null){
                authProvider.authenticate(session.token)
            } else
                null

            if(principal != null){
                call.respond(officeManager.officeList())
            } else {
                call.respondText("authentication failed")
            }
        }
    }*/
}