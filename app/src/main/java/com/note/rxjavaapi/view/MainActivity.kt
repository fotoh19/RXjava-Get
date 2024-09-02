package com.note.rxjavaapi.view

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.note.rxjavaapi.R
import com.note.rxjavaapi.data.RetrofitInstance
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class MainActivity : AppCompatActivity() {
    private lateinit var fetchButton: Button
    private lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fetchButton = findViewById(R.id.fetchButton)
        textView = findViewById(R.id.textView)

        fetchButton.setOnClickListener {
            fetchData()
        }
    }

    @SuppressLint("CheckResult")
    private fun fetchData() {
        RetrofitInstance.api.getPosts()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ posts ->
                val stringBuilder = StringBuilder()
                for (post in posts) {
                    stringBuilder.append("Title: ${post.title}\nBody: ${post.body}\n\n")
                }
                textView.text = stringBuilder.toString()
            }, { error ->
                Log.e("MainActivity", "Error: ${error.message}")
            })
    }
}