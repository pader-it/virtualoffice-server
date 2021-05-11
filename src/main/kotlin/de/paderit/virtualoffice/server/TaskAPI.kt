package de.paderit.virtualoffice.server

import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import java.time.LocalDateTime

fun Route.taskApi(taskManager: TaskManager, employeeRegistry: EmployeeRegistry){
    authenticate("auth-jwt"){
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
                get{
                    val taskid = call.parameters["taskid"]!!.toInt()
                    val task = taskManager.getTask(taskid)
                    if(task != null){
                        val taskResponse = TaskResponse(task.id, task.name, task.creator, task.creationTime,
                            task.assigned, task.status.toString())
                        call.respond(taskResponse)
                    } else {
                        call.response.status(HttpStatusCode.NotFound)
                    }
                }
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
data class TaskResponse(val id: Int,
                        val name: String,
                        val creator: Int,
                        val creationTime: LocalDateTime,
                        val assigned: MutableList<Int>,
                        val status: String)