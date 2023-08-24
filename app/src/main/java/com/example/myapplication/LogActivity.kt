package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myapplication.databinding.ActivityContactBinding
import com.example.myapplication.databinding.ActivityLogBinding
import com.example.myapplication.databinding.ActivityLogInBinding

class LogActivity : AppCompatActivity() {
    lateinit var binding: ActivityLogBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLogBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /*setSupportActionBar(binding.ltoolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)*/

        binding.protoImg.setOnClickListener {
            binding.protoImg.setImageResource(R.drawable.proto2)
        }
    }
}