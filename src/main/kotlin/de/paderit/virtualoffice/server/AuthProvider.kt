package de.paderit.virtualoffice.server

import io.ktor.auth.*

class AuthProvider {
    private var userTable: MutableMap<String, String> = mutableMapOf()
    private var userSession: MutableMap<String, UserIdPrincipal> = mutableMapOf()

    init{
        addUser("pader", "it")
    }

    fun authenticate(username: String, password: String): UserIdPrincipal? {
        return if(userTable.containsKey(username) && userTable[username] == password)
            UserIdPrincipal(username)
        else
            null
    }

    fun authenticate(token: String): UserIdPrincipal? {
        return if(userSession.containsKey(token))
            userSession[token]
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

    fun addSession(token: String, principal: UserIdPrincipal){
        userSession.put(token, principal)
    }
}