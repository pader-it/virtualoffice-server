package de.paderit.virtualoffice.server

import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
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
                val principal: JWTPrincipal = call.authentication.principal as JWTPrincipal
                call.respondText(principal.payload.getClaim("login").asString())
            }
        }
        route("/office"){
            get{
                call.respond(officeManager.officeList())
            }
        }
    }

}