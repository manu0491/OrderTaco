package com.wizeline.dependencyinjection.utils

import com.wizeline.dependencyinjection.data.Taco

object Utils {
    fun createTaco(): Taco {
        return Taco(
            type = "Suadero",
            tortilla = "maiz",
            note = "cebolla y cilantro",
            timestamp = 1682485200000 //April 26th, 2023
        )
    }

    fun createTacoList(): List<Taco> {
        return mutableListOf<Taco>(
            Taco(
                type = "Suadero",
                tortilla = "maiz",
                note = "cebolla y cilantro",
                timestamp = 1682485200000 //April 26th, 2023
            ),
            Taco(
                type = "Asada",
                tortilla = "harina",
                note = "pico de gallo y habanero",
                timestamp = 1682485200000 //April 26th, 2023
            ),
            Taco(
                type = "Pescado",
                tortilla = "harina",
                note = "ensalada de col y crema",
                timestamp = 1682485200000 //April 26th, 2023
            )
        )
    }
    fun createTacoListWithId(): List<Taco> {
        val list = createTacoList()
        val newList = mutableListOf<Taco>()
        var count = 0L
        for (taco in list){
            count++
            taco.id = count
            newList.add(taco)
        }
        return newList
    }
}