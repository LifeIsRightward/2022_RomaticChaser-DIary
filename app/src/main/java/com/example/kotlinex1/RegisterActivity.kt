package com.example.kotlinex1

import android.graphics.Color
import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.kotlinex1.databinding.ActivityLoginBinding
import com.example.kotlinex1.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {

    lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = "낭만파 다이어리"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setTitleTextColor(Color.BLACK)
        binding.toolbar.setBackgroundColor(Color.GREEN)
        auth = FirebaseAuth.getInstance()

        binding.RegisterBNT.setOnClickListener {
            CreateAccount(
                binding.RegisterUserEmail.text.toString(),
                binding.RegisterUserPassword.text.toString()
            )
        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item!!.itemId){
            android.R.id.home ->{//뒤로가기 버튼일때 그 액티비티 종료
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun CreateAccount(email: String, password: String) {//이메일 회원가입 구현
        if (email.isNotEmpty() && password.isNotEmpty()) {
            auth?.createUserWithEmailAndPassword(email, password)
                ?.addOnCompleteListener(this) { //통신이 완료되고 무슨 일을 할지 addOnCompleteListener
                        task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "계정 생성 완료.", Toast.LENGTH_SHORT).show()
                        finish() //가입창 종료
                    } else {
                        Toast.makeText(this, "계정 생성 실패", Toast.LENGTH_SHORT).show()
                        Log.e("asdfghjk", task.result.toString())
                    }
                }
        }
    }
}