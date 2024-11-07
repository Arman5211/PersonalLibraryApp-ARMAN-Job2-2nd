package com.example.personallibraryapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class BookAdapter : ListAdapter<BookLibrary, BookAdapter.ProfileViewHolder>(DiffCallback()) {

    private var onItemClickListener: ((BookLibrary) -> Unit)? = null
    private var onDeleteClickListener: ((BookLibrary) -> Unit)? = null
    private var onUpdateClickListener: ((BookLibrary) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.book_item, parent, false)
        return ProfileViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ProfileViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    fun setOnItemClickListener(listener: (BookLibrary) -> Unit) {
        onItemClickListener = listener
    }

    fun setOnDeleteClickListener(listener: (BookLibrary) -> Unit) {
        onDeleteClickListener = listener
    }

    fun setOnUpdateClickListener(listener: (BookLibrary) -> Unit) {
        onUpdateClickListener = listener
    }

    fun getBookAtPosition(position: Int): BookLibrary {
        return getItem(position)

    }

    inner class ProfileViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val Booktitle: TextView = itemView.findViewById(R.id.BookTitletxt)
        private val BookAuthor: TextView = itemView.findViewById(R.id.BookAuthorTxt)
        private val BookPage: TextView = itemView.findViewById(R.id.BookPageTxt)
        private val deleteButton: ImageButton = itemView.findViewById(R.id.deleteBtn)
        private val updateButton: ImageButton = itemView.findViewById(R.id.editBtn)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val profile = getItem(position)
                    onItemClickListener?.invoke(profile)
                }
            }

            deleteButton.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val profile = getItem(position)
                    onDeleteClickListener?.invoke(profile)
                }
            }

            updateButton.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val profile = getItem(position)
                    onUpdateClickListener?.invoke(profile)
                }
            }
        }

        fun bind(bookLibrary: BookLibrary) {
            Booktitle.text = bookLibrary.title
            BookAuthor.text = bookLibrary.author
            BookPage.text = bookLibrary.pages.toString()
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<BookLibrary>() {
        override fun areItemsTheSame(oldItem: BookLibrary, newItem: BookLibrary): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: BookLibrary, newItem: BookLibrary): Boolean {
            return oldItem == newItem
        }

    }
}