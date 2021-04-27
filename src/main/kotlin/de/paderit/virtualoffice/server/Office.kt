package de.paderit.virtualoffice.server

class Office(id: Int){
    private var members: MutableList<String> = mutableListOf()

    fun enterOffice(name: String){
        members.add(name)
    }

    fun leaveOffice(name: String){
        members.remove(name)
    }
}