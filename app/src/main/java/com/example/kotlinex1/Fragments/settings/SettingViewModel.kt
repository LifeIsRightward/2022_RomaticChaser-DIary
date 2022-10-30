package com.example.kotlinex1.Fragments.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SettingViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "KDH'S home fragment"
    }
    val text: LiveData<String> = _text
}