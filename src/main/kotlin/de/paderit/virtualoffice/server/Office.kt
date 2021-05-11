package de.paderit.virtualoffice.server

class Office(val id: Int){
    private var members: MutableList<Employee> = mutableListOf()

    fun enterOffice(emp: Employee){
        members.add(emp)
    }

    fun leaveOffice(emp: Employee){
        members.remove(emp)
    }
}