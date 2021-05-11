package de.paderit.virtualoffice.server

class Employee(val id: Int, val name: String, val email: String) {
    var office: Office? = null
    var tasks: MutableList<Int> = mutableListOf()
}