package com.example.kotlinex1.nav_Fragments.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SettingViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Setting_Fragment"
    }
    val text: LiveData<String> = _text
}