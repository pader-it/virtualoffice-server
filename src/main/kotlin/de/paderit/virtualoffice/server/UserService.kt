package de.paderit.virtualoffice.server

import io.ktor.auth.*

class UserService {
    private var userTable: MutableMap<String, String> = mutableMapOf()

    init{
        registerUser("pader", "it")
    }

    fun doesUserExist(username: String) = userTable.containsKey(username)

    fun authenticateUser(username: String, password: String): Boolean {
        return userTable.containsKey(username) && userTable[username] == password
    }

    fun registerUser(username: String, password: String): Boolean{
        return if(!userTable.containsKey(username)){
            userTable[username] = password
            true
        } else
            false
    }
}