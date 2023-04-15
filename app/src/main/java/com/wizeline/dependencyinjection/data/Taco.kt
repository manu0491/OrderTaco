package com.wizeline.dependencyinjection.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tacos")
data class Taco(val protein: String, val city: String) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}
