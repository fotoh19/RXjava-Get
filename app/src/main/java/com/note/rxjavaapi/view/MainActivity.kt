package com.note.rxjavaapi.view

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.note.rxjavaapi.R
import com.note.rxjavaapi.repository.PostRepository
import com.note.rxjavaapi.viewmodel.PostViewModel

@Suppress("UNCHECKED_CAST")
class MainActivity : AppCompatActivity() {
    private lateinit var fetchButton: Button
    private lateinit var textView: TextView
    private lateinit var postViewModel: PostViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fetchButton = findViewById(R.id.fetchButton)
        textView = findViewById(R.id.textView)

        val repository = PostRepository()

        postViewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return PostViewModel(repository) as T
            }
        })[PostViewModel::class.java]

        fetchButton.setOnClickListener {
            postViewModel.fetchPosts()
        }

        postViewModel.posts.observe(this) { posts ->
            val stringBuilder = StringBuilder()
            for (post in posts) {
                stringBuilder.append("Title: ${post.title}\nBody: ${post.body}\n\n")
            }
            textView.text = stringBuilder.toString()
        }

        postViewModel.error.observe(this) { error ->
            Toast.makeText(this, "Error: $error", Toast.LENGTH_LONG).show()
        }
    }
}