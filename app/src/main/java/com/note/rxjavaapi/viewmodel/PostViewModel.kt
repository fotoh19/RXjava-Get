package com.note.rxjavaapi.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.note.rxjavaapi.data.Post
import com.note.rxjavaapi.repository.PostRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers


class PostViewModel(private val repository: PostRepository) : ViewModel() {

    private val _posts = MutableLiveData<List<Post>>()
    val posts: LiveData<List<Post>> = _posts

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val disposable = CompositeDisposable()

    fun fetchPosts() {
        disposable.add(
            repository.getPosts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ postList ->
                    _posts.postValue(postList)
                }, { throwable ->
                    _error.postValue(throwable.message)
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}