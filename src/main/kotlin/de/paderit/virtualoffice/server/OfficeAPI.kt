package de.paderit.virtualoffice.server

import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.officeApi(employeeRegistry: EmployeeRegistry, officeManager: OfficeManager) {
    authenticate("auth-jwt"){
        route("/office"){
            get{
                call.respond(officeManager.officeList())
            }
            route("/{officeId}/enter"){
                post{
                    val officeId = call.parameters["officeId"]!!.toInt()
                    if(officeManager.hasOffice(officeId)){
                        val userid = retrieveUserID(call)
                        val emp = employeeRegistry.getEmployee(userid)!!
                        if(officeManager.enterOffice(officeId, emp)){
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
                        val userid = retrieveUserID(call)
                        val emp = employeeRegistry.getEmployee(userid)!!
                        if(officeManager.leaveOffice(officeId, emp)){
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