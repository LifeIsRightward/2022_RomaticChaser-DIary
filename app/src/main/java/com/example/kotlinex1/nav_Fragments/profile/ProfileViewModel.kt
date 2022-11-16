package com.example.kotlinex1.nav_Fragments.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProfileViewModel : ViewModel() {



    private val _text = MutableLiveData<String>().apply {
        value = "Profile_Fragment"
    }
    val text: LiveData<String> = _text


}
