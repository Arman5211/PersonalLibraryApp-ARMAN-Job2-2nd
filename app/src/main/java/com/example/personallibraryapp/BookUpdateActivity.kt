package com.example.personallibraryapp

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider

class BookUpdateActivity : AppCompatActivity() {
    private lateinit var profileViewModel: LibraryBookViewModel
    private lateinit var bookLibrary: BookLibrary

    private lateinit var nameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var dobEditText: EditText

    private lateinit var progressDialog: ProgressDialog

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_update)

        // Initialize ViewModel
        profileViewModel = ViewModelProvider(this).get(LibraryBookViewModel::class.java)

        // Retrieve user profile from intent
        bookLibrary = intent.getSerializableExtra("BOOK_LIBRARY") as BookLibrary

        // Initialize EditText fields
        nameEditText = findViewById(R.id.BookTitleEt)
        emailEditText = findViewById(R.id.BookAuthorEt)
        dobEditText = findViewById(R.id.BookPageEt)

        // Populate fields with user data
        populateFields()

        // Initialize progress dialog
        progressDialog = ProgressDialog(this).apply {
            setMessage("Updating Profile...")
            setCancelable(false) // Prevent closing while loading
        }

        // Update button click listener
        findViewById<Button>(R.id.updateBtn).setOnClickListener {
            if (validateInputs()) {
                showLoading() // Show loading before updating
                updateUserProfile() // Perform the update
            }
        }
    }

    private fun populateFields() {
        nameEditText.setText(bookLibrary.title)
        emailEditText.setText(bookLibrary.author)
        dobEditText.setText(bookLibrary.pages)
    }

    private fun validateInputs(): Boolean {
        val Title = nameEditText.text.toString().trim()
        val Author = emailEditText.text.toString().trim()
        val Page = dobEditText.text.toString().trim()

        return when {
            Title.isEmpty() -> {
                nameEditText.error = "Please enter your name"
                nameEditText.requestFocus()
                false
            }
            Author.isEmpty() -> {
                emailEditText.error = "Please enter your email"
                emailEditText.requestFocus()
                false
            }
            Page.isEmpty() -> {
                dobEditText.error = "Please enter your date of birth"
                dobEditText.requestFocus()
                false
            }

            else -> true
        }
    }

    private fun updateUserProfile() {
        // Retrieve input values
        val Title = nameEditText.text.toString()
        val Author = emailEditText.text.toString()
        val Page = dobEditText.text.toString()

        // Create updated user profile
        val updatedBookLibrary = BookLibrary(
            id = bookLibrary.id,
            title = Title,
            author = Author,
            pages = Page,
        )

        // Perform the update in ViewModel
        profileViewModel.updateUserProfile(updatedBookLibrary)

        // Delay to simulate loading, then close the activity
        Handler(Looper.getMainLooper()).postDelayed({
            hideLoading() // Hide loading after a delay
            Toast.makeText(this, "Profile updated successfully", Toast.LENGTH_SHORT).show()
            finish() // Close activity
        }, 2000) // Delay of 2 seconds
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