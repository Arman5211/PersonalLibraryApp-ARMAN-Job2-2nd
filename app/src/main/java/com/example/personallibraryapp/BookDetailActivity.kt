package com.example.personallibraryapp

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.text.NumberFormat

class BookDetailActivity : AppCompatActivity() {
    private lateinit var bookLibrary: BookLibrary

    private lateinit var IdTextView: TextView
    private lateinit var TitleTextView: TextView
    private lateinit var AuthorTextView: TextView
    private lateinit var PageTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_detail)

        // Retrieve the book information from the intent
        val bookLibrarySerializable = intent.getSerializableExtra("BOOK_LIBRARY")// Retrieve the book information from the intent

        // Check if the retrieved object is null
        if (bookLibrarySerializable is BookLibrary) {
            bookLibrary = bookLibrarySerializable
        } else {
            // Handle the error, possibly finish the activity or show a message
            finish() // Finish the activity if the data is not valid
            return
        }

        IdTextView = findViewById(R.id.BookIdLabelTextView)
        TitleTextView = findViewById(R.id.BooktitleabelTextView)
        AuthorTextView = findViewById(R.id.BookAuthoreabelTextView)
        PageTextView = findViewById(R.id.BookPageLabelTextView)

        populateFields()
    }

    private fun populateFields() {
        IdTextView.text = NumberFormat.getInstance().format(bookLibrary.id)
        TitleTextView.text = bookLibrary.title
        AuthorTextView.text = bookLibrary.author
        PageTextView.text = bookLibrary.pages
    }
}
