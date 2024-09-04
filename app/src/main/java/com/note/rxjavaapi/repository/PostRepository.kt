package com.note.rxjavaapi.repository

import com.note.rxjavaapi.data.ApiService
import com.note.rxjavaapi.data.Post
import io.reactivex.rxjava3.core.Observable

class PostRepository(private val apiService: ApiService) {

    fun getPosts(): Observable<List<Post>> {
        return apiService.getPosts()
    }
}
