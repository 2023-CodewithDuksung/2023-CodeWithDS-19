package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val backExecutor: ScheduledExecutorService = Executors.newSingleThreadScheduledExecutor()
        val mainExecutor: Executor = ContextCompat.getMainExecutor(this)
        backExecutor.schedule({
            mainExecutor.execute{

                MyApplication.auth.signOut() //TEST CODE...

                //로그인 검사
                if(MyApplication.checkAuth()) {
                    Log.d("ToyProject", "로그인 성공")
                    startActivity(Intent(applicationContext, MainActivity::class.java))
                    finish()
                } else {
                    Log.d("ToyProject", "로그인 실패")
                    startActivity(Intent(applicationContext, AuthActivity::class.java))
                    finish()
                }
            }
        }, 2, TimeUnit.SECONDS)
    }
}