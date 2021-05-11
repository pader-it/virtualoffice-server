package de.paderit.virtualoffice.server

class OfficeManager {
    private var offices: MutableMap<Int, Office> = mutableMapOf()
    private var idTracker = 1

    init{
        loadOffices()
    }

    fun createOffice(): Int {
        val newOffice = Office(idTracker)
        offices[idTracker] = newOffice
        idTracker++
        return newOffice.id
    }

    fun deleteOffice(id: Int): Boolean{
        return if(offices.containsKey(id)){
            offices.remove(id)
            true
        } else
            false
    }

    fun getOffice(id: Int): Office?{
        return if(offices.containsKey(id)){
            offices[id]
        } else {
            null
        }
    }

    fun enterOffice(id: Int, emp: Employee): Boolean{
        return if(offices.containsKey(id) && emp.office == null){
            offices[id]?.enterOffice(emp)
            emp.office = offices[id]
            true
        } else{
            false
        }
    }

    fun leaveOffice(id: Int, emp: Employee): Boolean{
        return if(offices.containsKey(id) && offices[id] == emp.office){
            offices[id]?.leaveOffice(emp)
            emp.office = null
            true
        } else{
            false
        }
    }

    fun hasOffice(id: Int) = offices.containsKey(id)

    fun officeList(): MutableList<Int>{
        var list = mutableListOf<Int>()
        offices.forEach{ (key, _) -> list.add(key) }
        return list
    }

    private fun loadOffices(){
        createOffice()
        createOffice()
    }

    private fun saveOffice(office: Office){

    }
}