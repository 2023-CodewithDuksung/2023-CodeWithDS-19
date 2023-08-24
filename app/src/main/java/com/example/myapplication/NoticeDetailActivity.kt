package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.example.myapplication.databinding.ActivityNoticeDetailBinding
import com.example.myapplication.databinding.FragmentHomeBinding

import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.firestore.Query
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.Overlay
import com.naver.maps.map.util.FusedLocationSource


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
class NoticeDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNoticeDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // view binding을 사용하여 레이아웃 파일 설정
        binding =ActivityNoticeDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


/*
*
val bundle : Bundle = Bundle()
                bundle.putString("host", data.host)
                bundle.putString("title", data.title)
                bundle.putString("departure", data.departure)
                bundle.putString("destination", data.destination)
                bundle.putString("currentDay", data.currentDay)
                bundle.putString("meetingTime", data.meetingTime)
                bundle.putString("recruitment", data.recruitment)
                bundle.putString("recruited", data.recruited)
                bundle.putString("context", data.context)
                bundle.putString("taxiOrWalk", data.taxiOrWalk)
* */

        // ------------- API : 영화 제목, 포스터 추가해야 함 --------------
        binding.textViewHostEmail.text = intent.getStringExtra("host")
        binding.textViewTaxiOrWalk.text = intent.getStringExtra("taxiOrWalk")
        binding.textViewDeparture.text = intent.getStringExtra("departure")
        binding.textViewDestination.text = intent.getStringExtra("destination")
        binding.textViewMeetingTime.text = intent.getStringExtra("meetingTime")

        binding.textViewRecruited.text = intent.getStringExtra("recruited")
        var recruitedStr = intent.getStringExtra("recruited")
        if( recruitedStr!= null && Integer.parseInt(recruitedStr) >= 4){
            binding.endOrIng.text="모집 완료"
        }


    }



}
