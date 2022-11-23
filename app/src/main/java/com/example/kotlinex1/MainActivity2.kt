package com.example.kotlinex1

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import com.google.android.material.snackbar.Snackbar
import androidx.navigation.ui.AppBarConfiguration
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.example.kotlinex1.nav_Fragments.diary.DiaryFragment
import com.example.kotlinex1.nav_Fragments.profile.ProfileFragment
import com.example.kotlinex1.nav_Fragments.settings.SettingFragment
import com.example.kotlinex1.nav_Fragments.todolist.ToDoListFragment
import com.example.kotlinex1.databinding.ActivityMain2Binding


class MainActivity2 : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMain2Binding
    private lateinit var toggle: ActionBarDrawerToggle

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

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        toggle = ActionBarDrawerToggle(this, binding.drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_open)
        toggle.syncState()

        binding.navView.setCheckedItem(R.id.nav_Profile)
        ReplaceFragment(ProfileFragment.newInstance())

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
                R.id.nav_Profile -> ReplaceFragment(ProfileFragment.newInstance())
                R.id.nav_Diary -> ReplaceFragment(DiaryFragment.newInstance())
                R.id.nav_To_Do_List -> ReplaceFragment(ToDoListFragment.newInstance())
                R.id.nav_Settings -> ReplaceFragment(SettingFragment.newInstance())
            }

            binding.drawerLayout.closeDrawer(GravityCompat.START)
            true
        }
    }

    private var backPressedTimeStamp = 0L
    override fun onBackPressed() {
        if(binding.navView.checkedItem?.itemId == R.id.nav_Profile){
            if (System.currentTimeMillis() - backPressedTimeStamp < 2000) {
                super.onBackPressed()
            } else {
                Snackbar.make(binding.root, "뒤로가기 버튼을 한번 더 누르세요.", Snackbar.LENGTH_SHORT)
                        .show()
            }
            backPressedTimeStamp = System.currentTimeMillis()
        }else{
            binding.navView.setCheckedItem(R.id.nav_Profile)
            ReplaceFragment(ProfileFragment.newInstance())
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


