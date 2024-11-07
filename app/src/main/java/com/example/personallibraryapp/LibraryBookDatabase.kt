package com.example.personallibraryapp

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [BookLibrary::class], version = 1,)
abstract class LibraryBookDatabase : RoomDatabase() {
    abstract fun userProfileDao(): LibraryBookDao

    companion object {
        @Volatile
        private var INSTANCE: LibraryBookDatabase? = null

        fun getDatabase(context: Context): LibraryBookDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    LibraryBookDatabase::class.java,
                    "library_book_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}