package de.paderit.virtualoffice.server

import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.taskApi(taskManager: TaskManager, employeeRegistry: EmployeeRegistry){
    authenticate {
        route("/tasks"){
            get{
                call.respond(taskManager.getTaskList())
            }
            route("/create"){
                post{
                    val req = call.receive<TaskCreationRequest>()
                    val emp = employeeRegistry.getEmployee(retrieveUserID(call))!!
                    val taskid = taskManager.createTask(req.name, emp)
                    call.respond(taskid)
                }
            }
            route("/{taskid}"){
                route("/assign"){
                    post{
                        val taskid = call.parameters["taskid"]!!.toInt()
                        val emp = employeeRegistry.getEmployee(retrieveUserID(call))!!
                        val task = taskManager.getTask(taskid)
                        if(task != null){
                            if(task.assign(emp)){
                                call.response.status(HttpStatusCode.OK)
                            } else {
                                call.response.status(HttpStatusCode.BadRequest)
                            }
                        } else {
                            call.response.status(HttpStatusCode.NotFound)
                        }
                    }
                }
                route("/disassign"){
                    post{
                        val taskid = call.parameters["taskid"]!!.toInt()
                        val emp = employeeRegistry.getEmployee(retrieveUserID(call))!!
                        val task = taskManager.getTask(taskid)
                        if(task != null){
                            if(task.disassign(emp)){
                                call.response.status(HttpStatusCode.OK)
                            } else {
                                call.response.status(HttpStatusCode.BadRequest)
                            }
                        } else {
                            call.response.status(HttpStatusCode.NotFound)
                        }
                    }
                }
                route("/close"){
                    post{
                        val taskid = call.parameters["taskid"]!!.toInt()
                        val task = taskManager.getTask(taskid)
                        if(task != null){
                            task.close()
                            call.response.status(HttpStatusCode.OK)
                        } else {
                            call.response.status(HttpStatusCode.NotFound)
                        }
                    }
                }
            }
        }
    }
}

data class TaskCreationRequest(val name: String)