package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.myapplication.databinding.ActivityAuthBinding
import com.example.myapplication.databinding.ActivityLogInBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider

class LogInActivity : AppCompatActivity() {
    lateinit var binding: ActivityLogInBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //toolbar
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""

        val state = intent.getStringExtra("auth")
        //로그인
        if (state == "login") {
            changeVisibility(state)
        }

        //회원가입
        else if (state == "signup") {
            changeVisibility(state)
        }

        binding.logInBtn.setOnClickListener {
            //이메일, 비밀번호 로그인.......................
            val email:String = binding.id.text.toString()
            val password:String = binding.password.text.toString()
            MyApplication.auth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this){ task ->
                    if(task.isSuccessful){
                        if(MyApplication.checkAuth()){
                            MyApplication.email = email
                            Toast.makeText(baseContext, "로그인 성공..", Toast.LENGTH_LONG).show()
                            startActivity(Intent(this, MainActivity::class.java))
                            finish()
                        }
                        else{
                            Toast.makeText(baseContext, "이메일 인증 실패..", Toast.LENGTH_LONG).show()
                            startActivity(Intent(this, AuthActivity::class.java))
                        }
                    }
                    else{
                        Toast.makeText(baseContext, "로그인 실패..", Toast.LENGTH_LONG).show()
                        startActivity(Intent(this, AuthActivity::class.java))
                    }
                    binding.id.text.clear()
                    binding.password.text.clear()
                }
        }

        binding.signUpBtn.setOnClickListener {
            //이메일,비밀번호 회원가입........................
            val email:String = binding.id.text.toString()
            val password:String = binding.password.text.toString()
            if (email.endsWith("@duksung.ac.kr")) { // Check if the email ends with "@duksung.ac.kr"
                MyApplication.auth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener(this){ task ->

                        if(task.isSuccessful){
                            // 이메일 2차 인증 작업
                            MyApplication.auth.currentUser?.sendEmailVerification()?.addOnCompleteListener{
                                    sendTask ->
                                if(sendTask.isSuccessful){
                                    // Log.d("mobileApp", "회원가입 성공..이메일 확인")
                                    Toast.makeText(baseContext, "회원가입 성공..이메일 확인", Toast.LENGTH_LONG).show()
                                    startActivity(Intent(this, AuthActivity::class.java))
                                }
                                else{
                                    // Log.d("mobileApp", "메일 전송 실패...")
                                    Toast.makeText(baseContext, "메일 전송 실패...", Toast.LENGTH_LONG).show()
                                    startActivity(Intent(this, AuthActivity::class.java))
                                }
                            }
                        }
                        else{
                            // Log.d("mobileApp", "회원가입 실패..")
                            Toast.makeText(baseContext, "회원가입 실패..", Toast.LENGTH_LONG).show()
                            startActivity(Intent(this, AuthActivity::class.java))
                        }
                        binding.id.text.clear()
                        binding.password.text.clear()
                    }

            }else {
                Toast.makeText(baseContext, "@duksung.ac.kr 도메인 이메일만 회원가입 가능합니다.", Toast.LENGTH_LONG).show()
            }
        }

//        val requestLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
//            val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
//            // ApiException : Google Play 서비스 호출이 실패했을 때 태스크에서 반환할 예외
//            try{
//                val account = task.getResult(ApiException::class.java) // account에 대한 정보를 따로 받음
//                val credential = GoogleAuthProvider.getCredential(account.idToken, null) // 인증 되었는지 확인
//                MyApplication.auth.signInWithCredential(credential)
//                    .addOnCompleteListener(this){ task ->
//                        if(task.isSuccessful){
//                            MyApplication.email = account.email
//                            // changeVisibility("login")
//                            Log.d("ToyProject", "GoogleSingIn - Successful")
//                            finish()
//                        }
//                        else {
//                            changeVisibility("logout")
//                            Log.d("ToyProject", "GoogleSingIn - NOT Successful")
//                        }
//                    }
//            } catch (e: ApiException){
//                changeVisibility("logout")
//                Log.d("ToyProject", "GoogleSingIn - ${e.message}")
//            }
//        }
//        binding.googleLoginBtn.setOnClickListener {
//            //구글 로그인....................
//            val gso : GoogleSignInOptions = GoogleSignInOptions
//                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestIdToken(getString(R.string.default_web_client_id))
//                .requestEmail()
//                .build()
//            val signInIntent : Intent = GoogleSignIn.getClient(this, gso).signInIntent
//            requestLauncher.launch(signInIntent)
//        }

        // firebase연결 후 google-json파일 연결해야함
//    }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    fun changeVisibility(state: String) {
        if (state == "login") {
            binding.title.text = "로그인"
            binding.logInBtn.visibility = View.VISIBLE
            binding.signUpBtn.visibility = View.GONE
        }

        //회원가입
        else if (state == "signup") {
            binding.title.text = "회원가입"
            binding.logInBtn.visibility = View.GONE
            binding.signUpBtn.visibility = View.VISIBLE
        }
    }
}