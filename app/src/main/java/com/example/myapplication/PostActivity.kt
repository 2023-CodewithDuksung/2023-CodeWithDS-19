package com.example.myapplication

import android.content.Intent
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
        var taxiOrWalk :String? = null

        binding.imgTexi.setOnClickListener{
            binding.choiceText.text = "택시를 선택하셨습니다!"
            binding.selectTexi.visibility = View.VISIBLE
            binding.imgTexi.setImageResource(R.drawable.select_texi)
            binding.imgWalk.setImageResource(R.drawable.by_walk)
            binding.selectWalk.visibility = View.INVISIBLE
            binding.descriptionText.visibility = View.VISIBLE
            taxiOrWalk = "taxi"

        }
        binding.imgWalk.setOnClickListener{
            binding.choiceText.text = "도보를 선택하셨습니다!"
            binding.selectWalk.visibility = View.VISIBLE
            binding.imgWalk.setImageResource(R.drawable.select_walk)
            binding.imgTexi.setImageResource(R.drawable.by_texi)
            binding.selectTexi.visibility = View.INVISIBLE
            binding.descriptionText.visibility = View.VISIBLE
            taxiOrWalk = "walk"

        }
        binding.nextButton.setOnClickListener {
            val intent = Intent(baseContext, FormActivity::class.java)
            intent.putExtra("taxiOrWalk", taxiOrWalk)
            startActivity(intent)
        }

    }
}