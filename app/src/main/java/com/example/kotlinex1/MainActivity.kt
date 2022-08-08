package com.example.kotlinex1

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.kotlinex1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.MainToolbar)
        supportActionBar?.title = "낭만파 다이어리"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.MainToolbar.setTitleTextColor(Color.BLACK)
        binding.MainToolbar.setBackgroundColor(Color.GREEN)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)//
        return super.onCreateOptionsMenu(menu)
    }//툴바 오른쪽에 search버튼과 더보기버튼이 보이게끔 해주는 온 크리에이트 옵션스 메뉴 함수

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item!!.itemId){
            android.R.id.home ->{//뒤로가기 버튼일때 그 액티비티 종료
                finish()
                return true
            }
            R.id.Search ->{
                Toast.makeText(this, "Search_ICon_Was_Clicked11", Toast.LENGTH_SHORT).show()
                return true
            }
            //더보기의 첫번째 항목을 클릭할때 토스트 메시지 실행
            R.id.action_1->{
                Toast.makeText(this, "I Don't Know but action_1 Was Clicked!!", Toast.LENGTH_SHORT).show()
                return true
            }
            //더보기의 주번째 항목을 클릭할때 토스트 메시지 실행
            R.id.action_2->{
                Toast.makeText(this, "I Don't Know but action_2 Was Clicked!!", Toast.LENGTH_SHORT).show()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}


