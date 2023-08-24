package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.example.myapplication.databinding.ActivityAuthBinding
import com.example.myapplication.databinding.ActivityLogInBinding

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