package com.example.kotlinex1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.kotlinex1.bootom_bar.HomeFragement
import com.example.kotlinex1.bootom_bar.SummaryFragment
import com.example.kotlinex1.bootom_bar.WriteFragement
import com.example.kotlinex1.databinding.ActivityWriteBinding
import com.example.kotlinex1.nav_Fragments.diary.DiaryVO


private const val TAG_WRITE = "write_fragment"
private const val TAG_HOME = "home_fragment"
private const val TAG_SUMMARY = "summary_fragment"

class WriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWriteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setFragment(TAG_HOME, WriteFragement())

        binding.navigationView.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.calenderFragment -> setFragment(TAG_WRITE, WriteFragement())
                R.id.homeFragment -> setFragment(TAG_HOME, HomeFragement())
                R.id.myPageFragment-> setFragment(TAG_SUMMARY, SummaryFragment())
            }
            true
        }

        val json = intent.getStringExtra("data") ?: return
        val data = DiaryVO.convertFromJson(json)
        //binding.txtWrite.text = "title: ${data.title}\ncontent: ${data.content}"
        //안에 글씨 보여주는거임 그 데이터를 불러와서 ㅇㅇ

    }

    private fun setFragment(tag: String, fragment: Fragment) {

        val manager : FragmentManager = supportFragmentManager
        val fragTransaction = manager.beginTransaction()

        if (manager.findFragmentByTag(tag) == null){
            fragTransaction.add(R.id.mainFrameLayout, fragment, tag)
        }

        val Write = manager.findFragmentByTag(TAG_WRITE)
        val Home = manager.findFragmentByTag(TAG_HOME)
        val Summary  = manager.findFragmentByTag(TAG_SUMMARY)

        if (Write != null){
            fragTransaction.hide(Write)
        }

        if (Home != null){
            fragTransaction.hide(Home)
        }

        if (Summary != null) {
            fragTransaction.hide(Summary)
        }

        if (tag == TAG_WRITE) {
            if (Write!=null){
                fragTransaction.show(Write)
            }
        }
        else if (tag == TAG_HOME) {
            if (Home != null) {
                fragTransaction.show(Home)
            }
        }

        else if (tag == TAG_SUMMARY){
            if (Summary != null){
                fragTransaction.show(Summary)
            }
        }

        fragTransaction.commitAllowingStateLoss()
    }
}






