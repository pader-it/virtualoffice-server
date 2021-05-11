package de.paderit.virtualoffice.server

class EmployeeRegistry {
    private var registry: MutableMap<Int, Employee> = mutableMapOf()
    private var idTracker: Int = 1

    fun createEmployee(name: String, email: String): Int{
        val emp = Employee(idTracker, name, email)
        idTracker++
        registry[emp.id] = emp
        saveEmployee(emp)
        return emp.id
    }

    fun getEmployee(id: Int): Employee? {
        return if(registry.containsKey(id)){
            registry[id]
        } else {
            null
        }
    }

    private fun loadEmployees(){
        // load idTracker
        // load employees
    }

    private fun saveEmployee(emp: Employee){
        // save idTracker
        // save employees
    }
}