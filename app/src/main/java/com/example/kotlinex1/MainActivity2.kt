package com.example.kotlinex1

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.replace
import com.example.kotlinex1.Fragments.diary.DiaryFragment
import com.example.kotlinex1.Fragments.profile.ProfileFragment
import com.example.kotlinex1.Fragments.settings.SettingFragment
import com.example.kotlinex1.Fragments.todolist.ToDoListFragment
import com.example.kotlinex1.databinding.ActivityMain2Binding
import kotlin.math.log


class MainActivity2 : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMain2Binding

    fun ReplaceFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.nav_host_fragment_content_main, fragment)
        transaction.commit()
        //supportFragmentManager => 프레그먼트 매니져(뭐 교체, 삭제, 등등)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.Navigationtoolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)//home 프레그먼트의 툴바의 기본설정된 텍스트가 안보이도록 설정
//        binding.Navigationtoolbar.setBackgroundColor(Color.argb(80,0,0,0))
        binding.fab.setBackgroundColor(Color.argb(80,0,0,0))

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowCustomEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_action_menu)

        ReplaceFragment(ProfileFragment())


        //메일모양 아이콘 ㅇㅇ
        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        val drawerLayout: DrawerLayout = binding.drawerLayout
//        val navView: NavigationView = binding.navView
//        val navController = findNavController(R.id.nav_host_fragment_content_main)
//        // Passing each menu ID as a set of Ids because each
//        // menu should be considered as top level destinations.
//
//        appBarConfiguration = AppBarConfiguration(
//            setOf(
//                R.id.nav_profile, R.id.nav_diary, R.id.nav_todolist, R.id.nav_setting
//            ), drawerLayout
//        )
//        setupActionBarWithNavController(navController, appBarConfiguration)
//        navView.setupWithNavController(navController)

        binding.navView.setNavigationItemSelectedListener {
            Log.e("error", "${it.itemId}")

            when(it.itemId){
                R.id.nav_Profile -> ReplaceFragment(ProfileFragment())
                R.id.nav_Diary -> ReplaceFragment(DiaryFragment())
                R.id.nav_To_Do_List -> ReplaceFragment(ToDoListFragment())
                R.id.nav_Settings -> ReplaceFragment(SettingFragment())
            }
            true
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main_activity2, menu)
        return true
    }

//    override fun onSupportNavigateUp(): Boolean {
//        val navController = findNavController(R.id.nav_host_fragment_content_main)
//        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
//    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> {
                binding.drawerLayout.openDrawer(GravityCompat.START)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}


