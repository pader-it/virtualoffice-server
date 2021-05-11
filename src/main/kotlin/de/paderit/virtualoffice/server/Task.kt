package de.paderit.virtualoffice.server

import java.time.LocalDateTime

class Task(id: Int, name: String, emp: Employee) {
    var creator: Int = emp.id
    var creationTime: LocalDateTime = LocalDateTime.now()
    var assignees: MutableList<Int> = mutableListOf()
    var status: TaskStatus = TaskStatus.OPEN

    fun assign(emp: Employee){
        assignees.add(emp.id)
        status = TaskStatus.ASSIGNED
    }

    fun disassign(emp: Employee){
        assignees.remove(emp)
    }

    fun close(){
        status = TaskStatus.CLOSED
    }
}