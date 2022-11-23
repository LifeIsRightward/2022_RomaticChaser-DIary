package com.example.kotlinex1.nav_Fragments.settings

import android.os.Bundle
import android.preference.PreferenceFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.example.kotlinex1.R
import com.example.kotlinex1.databinding.FragmentSettingBinding

class SettingFragment private constructor() : PreferenceFragmentCompat() {

    companion object {
        fun newInstance() = SettingFragment()
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preference, rootKey)

    }
}