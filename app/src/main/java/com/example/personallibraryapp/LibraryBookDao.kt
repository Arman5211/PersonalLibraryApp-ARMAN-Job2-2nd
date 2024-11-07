package com.example.personallibraryapp

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface LibraryBookDao {
    @Insert
    suspend fun insert(bookLibrary: BookLibrary)

    @Update
    suspend fun update(bookLibrary: BookLibrary)

    @Delete
    suspend fun delete(bookLibrary: BookLibrary)

    @Query("SELECT * FROM books")
    fun getAllUserProfiles(): LiveData<List<BookLibrary>>
}