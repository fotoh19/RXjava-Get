package com.note.rxjavaapi.repository

import com.note.rxjavaapi.data.Post
import com.note.rxjavaapi.data.RetrofitInstance
import io.reactivex.rxjava3.core.Observable

class PostRepository {

    fun getPosts(): Observable<List<Post>> {
        return RetrofitInstance.api.getPosts()
    }
}