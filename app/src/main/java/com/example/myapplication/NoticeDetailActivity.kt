package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.SyncStateContract.Helpers.update
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
import com.example.myapplication.MyApplication.Companion.db
import com.example.myapplication.databinding.ActivityNoticeDetailBinding
import com.example.myapplication.databinding.FragmentHomeBinding

import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.Query
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.Overlay
import com.naver.maps.map.util.FusedLocationSource


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
class NoticeDetailActivity : AppCompatActivity() {

    lateinit var binding: ActivityNoticeDetailBinding
    lateinit var mAuth: FirebaseAuth
    lateinit var myDocId: String
    lateinit var myChatId: String
    lateinit var myId: String
    lateinit var myUsers: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // view binding을 사용하여 레이아웃 파일 설정
        binding = ActivityNoticeDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        mAuth = FirebaseAuth.getInstance()

        myId = mAuth.currentUser!!.uid
        myDocId = intent.getStringExtra("myDocId").toString()
        myChatId = intent.getStringExtra("myChatId").toString()

        Log.d("ToyProject", "myDocId: ${myDocId}")

        binding.btnGoChatting.setOnClickListener {
            if(MyApplication.checkAuth()) {
                MyApplication.db.collection("notices").document(myDocId)
                    .get()
                    .addOnSuccessListener {
                        val query = it.data
                        Log.d("ToyProject", "${query}")

                        val recruited = query!!.get("recruited").toString()
                        val recruitment = query!!.get("recruitment").toString()

                        if (recruitment.toInt() - recruited.toInt() > 0) {
                            val cnt = recruited.toInt() + 1
                            MyApplication.db.collection("Talks").document(myChatId)
                                .update("usersId", FieldValue.arrayUnion(myId))
                                .addOnSuccessListener {
                                    Log.d("ToyProject", "신청성공")

                                    MyApplication.db.collection("notices").document(myDocId)
                                        .update("recruited", cnt.toString())
                                        .addOnSuccessListener {
                                            val intent = Intent(this, ChatActivity::class.java)
                                            intent.putExtra("chatId", myChatId)
                                            startActivity(intent)
                                        }
                                }
                                .addOnFailureListener {
                                    Log.d("ToyProject", "Talks 컬렉션 업데이트 실패")
                                }
                        } else {
                            Log.d("ToyProject", "신청 실패 - 모집 종료")
                        }
                    }
                    .addOnFailureListener {
                        Log.d("ToyProject", "notices 컬렉션 읽기 실패")
                    }

            }
        }

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
