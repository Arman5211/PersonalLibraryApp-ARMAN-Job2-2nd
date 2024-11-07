package com.example.personallibraryapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LibraryBookViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: LibraryBookRepository

    init {
        val userProfileDao = LibraryBookDatabase.getDatabase(application).userProfileDao()
        repository = LibraryBookRepository(userProfileDao)
    }

    fun getUserProfiles(): LiveData<List<BookLibrary>> {
        return repository.getUserProfiles()
    }

    fun insertUserProfile(bookLibrary: BookLibrary) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insert(bookLibrary)
        }
    }

    fun updateUserProfile(bookLibrary: BookLibrary) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.update(bookLibrary)
        }
    }

    fun deleteUserProfile(bookLibrary: BookLibrary) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.delete(bookLibrary)
        }
    }
}