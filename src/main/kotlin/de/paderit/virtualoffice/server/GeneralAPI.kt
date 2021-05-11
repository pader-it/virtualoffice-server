package de.paderit.virtualoffice.server

import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.generalApi(employeeRegistry: EmployeeRegistry){
    authenticate("auth-jwt") {
        route("/hello") {
            get {
                call.respondText("Hello World!")
            }
        }
        route("/userinfo") {
            get {
                val emp = employeeRegistry.getEmployee(retrieveUserID(call))
                if (emp != null) {
                    call.respond(emp.name)
                } else {
                    call.response.status(HttpStatusCode.BadRequest)
                }
            }
        }
    }
}

fun retrieveUserID(call: ApplicationCall): Int{
    val principal = call.authentication.principal as JWTPrincipal
    return principal.payload.getClaim("id").asInt()
}