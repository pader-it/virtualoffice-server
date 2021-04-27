package de.paderit.virtualoffice.server

import io.ktor.auth.*

class AuthProvider {
    private var userTable: MutableMap<String, String> = mutableMapOf()

    init{
        addUser("pader", "it")
    }

    fun authenticate(username: String, password: String): UserIdPrincipal? {
        return if(userTable.containsKey(username) && userTable[username] == password)
            UserIdPrincipal(username)
        else
            null
    }

    fun addUser(username: String, password: String): Boolean{
        return if(!userTable.containsKey(username)){
            userTable[username] = password
            true
        } else
            false
    }
}