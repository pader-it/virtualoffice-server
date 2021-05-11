package de.paderit.virtualoffice.server

class Office(val id: Int){
    var members: MutableList<Employee> = mutableListOf()

    fun enterOffice(emp: Employee){
        members.add(emp)
    }

    fun leaveOffice(emp: Employee){
        members.remove(emp)
    }

    fun getMemberIdList(): MutableList<Int>{
        val memberIdList = mutableListOf<Int>()
        members.forEach {
            memberIdList.add(it.id)
        }
        return memberIdList
    }
}