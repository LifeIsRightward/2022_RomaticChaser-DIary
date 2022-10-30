package com.example.kotlinex1.Fragments.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProfileViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "KDHds'S home fragment"
    }
    val text: LiveData<String> = _text
}