package com.note.rxjavaapi.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.note.rxjavaapi.data.Post
import com.note.rxjavaapi.repository.PostRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class MainViewModel(private val repository: PostRepository) : ViewModel() {

    private val _posts = MutableLiveData<UiState<List<Post>>>()
    val posts: LiveData<UiState<List<Post>>> get() = _posts

    @SuppressLint("CheckResult")
    fun getPosts() {
        _posts.value = UiState.Loading
        repository.getPosts()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { postList -> _posts.value = UiState.Success(postList) },
                { error -> _posts.value = UiState.Error(error.message ?: "Unknown Error") }
            )
    }
}
sealed class UiState<out T> {
    data object Loading : UiState<Nothing>()
    data class Success<T>(val data: T) : UiState<T>()
    data class Error(val message: String) : UiState<Nothing>()
}

