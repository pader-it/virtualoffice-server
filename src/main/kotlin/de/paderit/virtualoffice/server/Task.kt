package de.paderit.virtualoffice.server

import java.time.LocalDateTime

class Task(val id: Int, val name: String, emp: Employee) {
    var creator: Int = emp.id
    var creationTime: LocalDateTime = LocalDateTime.now()
    var assignees: MutableList<Int> = mutableListOf()
    var status: TaskStatus = TaskStatus.OPEN

    fun assign(emp: Employee): Boolean{
        return if(!assignees.contains(emp.id)){
            assignees.add(emp.id)
            status = TaskStatus.ASSIGNED
            true
        } else {
            false
        }
    }

    fun disassign(emp: Employee): Boolean{
        return if(assignees.contains(emp.id)){
            assignees.remove(emp)
            true
        } else {
            false
        }
    }

    fun close(){
        status = TaskStatus.CLOSED
    }
}