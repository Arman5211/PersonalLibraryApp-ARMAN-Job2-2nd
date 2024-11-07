package com.example.personallibraryapp

import android.app.ProgressDialog
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider

class AddBookActivity : AppCompatActivity() {

    private lateinit var profileViewModel: LibraryBookViewModel
    private lateinit var progressDialog: ProgressDialog // ProgressDialog variable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_book)

        profileViewModel = ViewModelProvider(this).get(LibraryBookViewModel::class.java)

        val TitleEditText = findViewById<EditText>(R.id.BookTitleEt)
        val AuthorEditText = findViewById<EditText>(R.id.BookAuthorEt)
        val BookpageEditText = findViewById<EditText>(R.id.BookPageEt)

        // Initialize ProgressDialog
        progressDialog = ProgressDialog(this).apply {
            setMessage("Adding Profile...")
            setCancelable(false) // Prevents closing while loading
        }

        findViewById<Button>(R.id.addBtn).setOnClickListener {
            if (validateInputs(TitleEditText,AuthorEditText ,BookpageEditText)) {
                showLoading() // Show loading before adding profile

                val Title = TitleEditText.text.toString().trim()
                val Author = AuthorEditText.text.toString().trim()
                val page = BookpageEditText.text.toString().trim()

                val bookLibrary = BookLibrary(id = 0, title = Title, author = Author, pages = page,)
                profileViewModel.insertUserProfile(bookLibrary)

                // Simulate loading and delay for 2 seconds
                BookpageEditText.postDelayed({
                    hideLoading() // Hide loading after a delay
                    Toast.makeText(this, "Profile added successfully", Toast.LENGTH_SHORT).show()
                    finish() // Close the activity
                }, 2000) // 2 seconds delay
            }
        }
    }

    private fun validateInputs(
        titleEditText: EditText,
        authorEditText: EditText,
        pageEditText: EditText
    ): Boolean {
        val title = titleEditText.text.toString().trim()
        val author = authorEditText.text.toString().trim()
        val page = pageEditText.text.toString().trim()

        return when {
            title.isEmpty() -> {
                titleEditText.error = "Please enter your name"
                titleEditText.requestFocus()
                false
            }
            author.isEmpty() -> {
                authorEditText.error = "Please enter your email"
                authorEditText.requestFocus()
                false
            }

            page.isEmpty() -> {
                pageEditText.error = "Please enter your date of birth"
                pageEditText.requestFocus()
                false
            }

            else -> true // All inputs are valid
        }
    }

    private fun showLoading() {
        // Show the progress dialog
        progressDialog.show()
    }

    private fun hideLoading() {
        // Dismiss the progress dialog
        progressDialog.dismiss()
    }
}