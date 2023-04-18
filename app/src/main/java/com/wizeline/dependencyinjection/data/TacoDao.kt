package com.wizeline.dependencyinjection.data

import android.database.Cursor
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TacoDao {

    @Query("SELECT * FROM tacos ORDER BY id DESC")
    suspend fun getAll(): List<Taco>

    @Insert
    suspend fun inserAll(vararg tacos: Taco)

    @Delete
    suspend fun deleteTaco(vararg tacos: Taco)

    @Query("DELETE FROM tacos")
    suspend fun deleteTable()

    @Query("SELECT * FROM tacos ORDER BY id DESC")
    fun selectAllTacosCursor(): Cursor

    @Query("SELECT * FROM tacos WHERE id =:id")
    fun selectTacoById(id: Long): Cursor?
}