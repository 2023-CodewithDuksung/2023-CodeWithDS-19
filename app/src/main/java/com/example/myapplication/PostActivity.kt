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
            binding.selectTexi.visibility = View.VISIBLE
            binding.selectWalk.visibility = View.INVISIBLE
            binding.descriptionText.visibility = View.VISIBLE

        }
        binding.imgWalk.setOnClickListener{
            binding.choiceText.text = "도보를 선택하셨습니다!"
            binding.selectWalk.visibility = View.VISIBLE
            binding.selectTexi.visibility = View.INVISIBLE
            binding.descriptionText.visibility = View.VISIBLE

        }

    }
}