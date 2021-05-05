package de.paderit.virtualoffice.server

import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import io.ktor.http.*
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
                call.respondText(retrieveUser(call))
            }
        }
        route("/office"){
            get{
                call.respond(officeManager.officeList())
            }
            route("/{officeId}/enter"){
                post{
                    val officeId = call.parameters["officeId"]!!.toInt()
                    if(officeManager.hasOffice(officeId)){
                        val user = retrieveUser(call)
                        if(officeManager.isUserFree(user)){
                            officeManager.enterOffice(officeId, user)
                            call.response.status(HttpStatusCode.OK)
                        } else {
                            call.response.status(HttpStatusCode.BadRequest)
                        }
                    } else {
                        call.response.status(HttpStatusCode.NotFound)
                    }
                }
            }
            route("/{officeId}/leave"){
                post{
                    val officeId = call.parameters["officeId"]!!.toInt()
                    if(officeManager.hasOffice(officeId)){
                        val user = retrieveUser(call)
                        if(officeManager.whereIsUser(user) == officeId){
                            officeManager.leaveOffice(officeId, user)
                            call.response.status(HttpStatusCode.OK)
                        } else {
                            call.response.status(HttpStatusCode.BadRequest)
                        }
                    } else {
                        call.response.status(HttpStatusCode.NotFound)
                    }
                }
            }
        }
    }
}

fun retrieveUser(call: ApplicationCall): String{
    val principal = call.authentication.principal as JWTPrincipal
    return principal.payload.getClaim("login").asString()
}