package com.example.kotlinex1.nav_Fragments.todolist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ToDoListViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "ToDoList_Fragment"
    }
    val text: LiveData<String> = _text
}