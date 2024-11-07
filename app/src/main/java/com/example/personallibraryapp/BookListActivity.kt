package com.example.personallibraryapp

import android.content.Intent
import android.graphics.Canvas
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class BookListActivity : AppCompatActivity() {

    private lateinit var bookViewModel: LibraryBookViewModel
    private lateinit var bookAdapter: BookAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_list)

        bookViewModel = ViewModelProvider(this).get(LibraryBookViewModel::class.java)

        val recyclerView = findViewById<RecyclerView>(R.id.profileRecyclerView)
        bookAdapter = BookAdapter()

        recyclerView.adapter = bookAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)


        bookViewModel.getUserProfiles().observe(this, Observer { profiles ->
            bookAdapter.submitList(profiles)
        })


        bookAdapter.setOnItemClickListener { bookLibrary ->
            val intent = Intent(this, BookDetailActivity::class.java).apply {
                putExtra("BOOK_LIBRARY", bookLibrary)
            }
            startActivity(intent)
        }


        bookAdapter.setOnUpdateClickListener { bookLibrary ->
            val intent = Intent(this, BookUpdateActivity::class.java).apply {
                putExtra("BOOK_LIBRARY", bookLibrary)
            }
            startActivity(intent)
        }


        findViewById<FloatingActionButton>(R.id.addProfileBtn).setOnClickListener {
            startActivity(Intent(this, AddBookActivity::class.java))
        }

        setupSwipeToDelete(recyclerView)
    }


    private fun showDeleteConfirmationDialog(bookLibrary: BookLibrary) {
        AlertDialog.Builder(this)
            .setTitle("Delete Book")
            .setMessage("Are you sure you want to delete this Book?")
            .setPositiveButton("Yes") { dialog, _ ->
                bookViewModel.deleteUserProfile(bookLibrary)  // Delete the profile
                Toast.makeText(this, "Book deleted", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }
            .setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()  // Cancel delete operation
            }
            .create()
            .show()
    }

    private fun setupSwipeToDelete(recyclerView: RecyclerView) {
        val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val bookToDelete = bookAdapter.getBookAtPosition(position)


                showDeleteConfirmationDialog(bookToDelete)
            }

            override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
                                     dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)

            }
        })

        itemTouchHelper.attachToRecyclerView(recyclerView)
    }
}