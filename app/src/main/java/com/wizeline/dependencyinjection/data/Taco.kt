package com.wizeline.dependencyinjection.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tacos")
data class Taco(val type: String, val tortilla: String, val note: String, val timestamp: Long) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}
