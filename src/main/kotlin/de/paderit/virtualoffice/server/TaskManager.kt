package de.paderit.virtualoffice.server

class TaskManager {
    private var tasks: MutableMap<Int, Task> = mutableMapOf()
    private var idTracker = 1

    fun createTask(name: String, emp: Employee): Int{
        val task = Task(idTracker, name, emp)
        tasks[task.id] = task
        idTracker++
        return task.id
    }

    fun getTask(id: Int): Task?{
        return if(tasks.containsKey(id)){
            tasks[id]
        } else{
            null
        }
    }

    fun getTaskList(): List<Int> {
        return tasks.keys.toList()
    }
}