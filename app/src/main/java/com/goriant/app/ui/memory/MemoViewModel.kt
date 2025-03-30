package com.goriant.app.ui.memory

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MemoViewModel : ViewModel() {

    private val _texts = MutableLiveData<List<String>>().apply {
        value = (1..100).mapIndexed { _, i ->
            "This is item # $i"
        }
    }

    val texts: LiveData<List<String>> = _texts
}