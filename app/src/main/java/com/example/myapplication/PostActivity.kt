package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.myapplication.databinding.ActivityPostBinding

class PostActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPostBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityPostBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.imgTexi.setOnClickListener{
            binding.choiceText.text = "택시를 선택하셨습니다!"
            binding.selectText.visibility = View.VISIBLE
            binding.descriptionText.visibility = View.VISIBLE

        }

    }
}