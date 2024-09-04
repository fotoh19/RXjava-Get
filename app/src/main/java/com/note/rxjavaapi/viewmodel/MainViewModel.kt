package com.note.rxjavaapi.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.note.rxjavaapi.data.Post
import com.note.rxjavaapi.repository.PostRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class MainViewModel(private val repository: PostRepository) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    private val _posts = MutableLiveData<List<Post>>()
    val posts: LiveData<List<Post>> get() = _posts

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun fetchPosts() {
        compositeDisposable.add(
            repository.getPosts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { postList -> _posts.value = postList },
                    { throwable -> _error.value = throwable.message }
                )
        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}