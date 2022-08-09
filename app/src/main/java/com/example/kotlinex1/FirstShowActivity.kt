package com.example.kotlinex1

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlinex1.databinding.ActivityFirstshowBinding
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider


class FirstShowActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFirstshowBinding//이건 로그인액티비티의 뷰바인딩을 하는거임 이러면 findviewid(R.id.xx)쓸 필요가없음

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =
            ActivityFirstshowBinding.inflate(layoutInflater)//뷰바인딩인 binding 선언 inflate(layoutInflate)로
        setContentView(binding.root)//이게 원래는 레이아웃으로 괄호안에 들어가야하는데 뷰바인딩을 사용해서 바인딩.루트로 하면 됨

        binding.Start.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

}