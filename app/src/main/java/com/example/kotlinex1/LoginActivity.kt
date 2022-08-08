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
import com.example.kotlinex1.databinding.ActivityLoginBinding
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider


class LoginActivity : AppCompatActivity() {

    lateinit var auth: FirebaseAuth/*lateinit var 같은 경우는 원래 선언과 동시에 초기화를 해야하는데 선언만 일단 해주겠다는 키워드임
    (일단 컴파일상 오류는 없게끔 만들어주는 키워드인듯) 그리고 auth라는 이름의 FirebaseAuth객체 생성준비*/
    private lateinit var binding: ActivityLoginBinding//이건 로그인액티비티의 뷰바인딩을 하는거임 이러면 findviewid(R.id.xx)쓸 필요가없음
    private lateinit var googleSignInClient: GoogleSignInClient
    private var googleActivityResult: ActivityResultLauncher<Intent>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =
            ActivityLoginBinding.inflate(layoutInflater)//뷰바인딩인 binding 선언 inflate(layoutInflate)로
        setContentView(binding.root)//이게 원래는 레이아웃으로 괄호안에 들어가야하는데 뷰바인딩을 사용해서 바인딩.루트로 하면 됨
        auth = FirebaseAuth.getInstance()//Firebase 객체 생성

        setSupportActionBar(binding.LoginToolbar)
        supportActionBar?.title = "낭만파 다이어리"
        binding.LoginToolbar.setTitleTextColor(Color.BLACK)
        binding.LoginToolbar.setBackgroundColor(Color.GREEN)
        //GoogleSignInClient 객체 초기화
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN) //기본 로그인 방식 사용
            .requestIdToken(getString(R.string.default_web_client_id))
            //requestIdToken :필수사항이다. 사용자의 식별값(token)을 사용하겠다.
            //(App이 구글에게 요청)
            .requestEmail()
            // 사용자의 이메일을 사용하겠다.(App이 구글에게 요청)
            .build()

        // 내 앱에서 구글의 계정을 가져다 쓸거니 알고 있어라!
        googleSignInClient = GoogleSignIn.getClient(this, gso)

        binding.LoginGoogleBtn.setOnClickListener {
            googleLogin()
        }


        googleActivityResult =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                val intent: Intent? = if (it.data == null) null else it.data

                if (it.resultCode == RESULT_OK && intent != null) {
                    //결과 Intent(data 매개변수) 에서 구글로그인 결과 꺼내오기
                    val result = Auth.GoogleSignInApi.getSignInResultFromIntent(intent)

                    //정상적으로 로그인되었다면
                    if (result != null && result.isSuccess) {
                        //우리의 FireBase 서버에 사용자 이메일 정보보내기
                        val account = result.signInAccount
                        firebaseAuthWithGoogle(account) //이건 밑에서 구현됨
                    }
                } else {
                    Snackbar.make(binding.root, "Result Code Error", Snackbar.LENGTH_INDEFINITE)
                        .setAction("닫기") { }
                        .show()
                }
            }

        binding.LoginRegisterBtn.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
            //Register()//Register if you don't have account
        }
        binding.LOGINBtn.setOnClickListener {
            Login()//Login if you have account
        }
    }

    fun Login() {//이메일 로그인 구현
        val ID: String = binding.LoginUserEmail.text.toString()
        val PW: String = binding.LoginUserPassword.text.toString()

        if ((ID.isEmpty()) || (PW.isEmpty())) {
            Toast.makeText(this, "Empty Imformation", Toast.LENGTH_LONG).show()
        } else {
            auth?.signInWithEmailAndPassword(
                binding.LoginUserEmail.text.toString(),
                binding.LoginUserPassword.text.toString()
            )?.addOnCompleteListener {//통신이 완료되고 무슨 일을 할지 addOnCompleteListener
                    task ->
                if (task.isSuccessful) {
                    //로그인, 아이디와 패스워드가 맞았을 때
                    moveMainPage(task.result?.user)
                } else {
                    //Show the error message, 아이디와 패스워드가 틀렸을 때
                    Toast.makeText(this, task.exception?.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    // 로그인이 성공하면 다음 페이지로 넘어가는 함수
    fun moveMainPage(user: FirebaseUser?) {
        // 파이어베이스 유저 상태가 있을 경우 다음 페이지로 넘어갈 수 있음
        if (user != null) {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    fun googleLogin() {
        //1. 구글로 로그인을 한다.
        val signInIntent = googleSignInClient.signInIntent
        googleActivityResult?.launch(signInIntent)
    }

    fun firebaseAuthWithGoogle(account: GoogleSignInAccount?) {
        //구글로부터 로그인된 사용자의 정보(Credentail)을 얻어온다.
        val credential = GoogleAuthProvider.getCredential(account?.idToken!!, null)
        //그 정보를 사용하여 Firebase의 auth를 실행한다.
        auth?.signInWithCredential(credential)?.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                //로그인 처리를 해주면 됨
                moveMainPage(task.result?.user)
            } else {
                //오류가 난 경우!
                Toast.makeText(this, task.exception?.message, Toast.LENGTH_LONG).show()
            }
            //progressBar.visibility = View.GONE
        }
    }

}