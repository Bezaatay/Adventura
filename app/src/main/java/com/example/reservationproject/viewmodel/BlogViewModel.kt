package com.example.reservationproject.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bezalibrary.service.Functions
import com.example.bezalibrary.service.model.BlogElement

class BlogViewModel : ViewModel() {

    private val functions = Functions()

    private val _blogPosts = MutableLiveData<List<BlogElement>>()
    val blogPosts: LiveData<List<BlogElement>?> get() = _blogPosts

    init {
        fetchBlogPost()
    }

    fun fetchBlogPost() {
        functions.getBlogPost().observeForever { blogs ->
            _blogPosts.value = blogs
        }
    }
}