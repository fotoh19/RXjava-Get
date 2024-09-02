package com.note.rxjavaapi.data

import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET

interface ApiService {
    @GET("posts")
    fun getPosts(): Observable<List<Post>>
}
