package com.example.personallibraryapp

import androidx.lifecycle.LiveData

class LibraryBookRepository(private val libraryBookDao: LibraryBookDao) {
    fun getUserProfiles(): LiveData<List<BookLibrary>> {
        return libraryBookDao.getAllUserProfiles()
    }

    suspend fun insert(bookLibrary: BookLibrary) {
        libraryBookDao.insert(bookLibrary)
    }


    suspend fun update(bookLibrary: BookLibrary) {
        libraryBookDao.update(bookLibrary)
    }

    suspend fun delete(bookLibrary: BookLibrary) {
        libraryBookDao.delete(bookLibrary)
    }
}
