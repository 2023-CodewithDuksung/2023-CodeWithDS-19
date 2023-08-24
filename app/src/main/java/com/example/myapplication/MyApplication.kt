package com.example.myapplication

import androidx.multidex.MultiDexApplication
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MyApplication : MultiDexApplication() {
    companion object {
        lateinit var db: FirebaseFirestore
        lateinit var auth: FirebaseAuth
        var email: String? = null

        //로그인 상태
        fun checkAuth(): Boolean {
            var currentuser = auth.currentUser
            return currentuser?.let {
                email = currentuser.email
                if (currentuser.isEmailVerified) true
                else false
            } ?: false
        }
    }


    override fun onCreate() {
        super.onCreate()
        db = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()
    }
}
