package de.paderit.virtualoffice.server

class OfficeManager {
    private var officeMap: MutableMap<Int, Office> = mutableMapOf()

    init{
        createOffice(10)
        createOffice(101)
    }

    fun createOffice(id: Int){
        if(!officeMap.containsKey(id)){
            officeMap[id] = Office(id)
        }
    }

    fun enterOffice(id: Int, name: String): Boolean{
        return if(officeMap.containsKey(id)){
            officeMap[id]?.enterOffice(name)
            true
        } else{
            false
        }
    }

    fun leaveOffice(id: Int, name: String): Boolean{
        return if(officeMap.containsKey(id)){
            officeMap[id]?.leaveOffice(name)
            true
        } else{
            false
        }
    }

    fun officeList(): MutableList<Int>{
        var list = mutableListOf<Int>()
        officeMap.forEach{ (key, _) -> list.add(key) }
        return list
    }

}