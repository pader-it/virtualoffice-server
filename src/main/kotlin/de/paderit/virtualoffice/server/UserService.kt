package de.paderit.virtualoffice.server

import io.ktor.auth.*

class UserService(private val employeeRegistry: EmployeeRegistry){
    private var userTable: MutableMap<String, String> = mutableMapOf()
    private var userIdMap: MutableMap<String, Int> = mutableMapOf()

    init{
        loadUsers()
    }

    fun doesUserExist(username: String) = userTable.containsKey(username)

    fun doesUserIdExist(userid: Int) = userIdMap.containsKey(userid)

    private fun checkPassword(username: String, password: String) = userTable[username] == password

    fun authenticateUser(username: String, password: String): Int {
        return if(doesUserExist(username) && checkPassword(username, password))
            userIdMap[username]!!
        else
            -1
    }

    fun registerUser(username: String, password: String, name: String, email: String): Boolean{
        return if(!userTable.containsKey(username)){
            userTable[username] = password
            userIdMap[username] = employeeRegistry.createEmployee(name, email)
            true
        } else
            false
    }

    private fun loadUsers(){
        registerUser("pader", "it", "Pader IT", "paderit@paderit.com")
    }

    private fun saveUser(){

    }
}