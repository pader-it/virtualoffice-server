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
            route("/{officeId}"){
                get{
                    val officeId = call.parameters["officeId"]!!.toInt()
                    val office = officeManager.getOffice(officeId)
                    if(office != null){
                        call.respond(OfficeResponse(office.id, office.getMemberIdList()))
                    } else {
                        call.response.status(HttpStatusCode.BadRequest)
                    }
                }
                route("/enter"){
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
                route("/leave"){
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
}

data class OfficeResponse(val id: Int, val members: MutableList<Int>)