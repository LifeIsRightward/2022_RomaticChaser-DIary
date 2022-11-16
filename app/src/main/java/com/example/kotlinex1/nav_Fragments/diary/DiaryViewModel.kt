package com.example.kotlinex1.nav_Fragments.diary

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DiaryViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Diary_Fragment"
    }
    val text: LiveData<String> = _text
}