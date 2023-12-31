package com.example.myapplication

import android.content.Intent
import android.Manifest
import android.content.pm.PackageManager
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
import androidx.core.app.ActivityCompat
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
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


class NoticeDetailActivity : AppCompatActivity(), OnMapReadyCallback {

    lateinit var binding: ActivityNoticeDetailBinding
    lateinit var mAuth: FirebaseAuth
    lateinit var myDocId: String
    lateinit var myChatId: String
    lateinit var myId: String
    lateinit var myUsers: String


    //현 위치
    private lateinit var mapView: MapView
    private val LOCATION_PERMISSTION_REQUEST_CODE: Int = 1000
    private lateinit var locationSource: FusedLocationSource // 위치를 반환하는 구현체
    private lateinit var naverMap: NaverMap

    private val PERMISSIONS = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )
    val markerList = arrayOf(
        listOf("덕성여대 정문", 37.652933, 127.016745),
        listOf("덕성여대 후문", 37.652135, 127.018054),
        listOf("가오리역", 37.641224, 127.016088),
        listOf("4.19민주묘지역", 37.649593, 127.013746),
        listOf("수유역", 37.637105, 127.024856),
        listOf("쌍문역", 37.648087, 127.034662),
        //
        listOf("덕성여대 기숙사", 37.651852, 127.017337),
        listOf("솔밭공원역", 37.656088, 127.013252),
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // view binding을 사용하여 레이아웃 파일 설정
        binding = ActivityNoticeDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        mapView = findViewById(R.id.detail_map)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)

        val fm = supportFragmentManager
        val mapFragment = fm.findFragmentById(R.id.detail_map) as MapFragment?



        locationSource = FusedLocationSource(this, LOCATION_PERMISSTION_REQUEST_CODE)

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
        binding.textViewTitle.text = intent.getStringExtra("title")
        binding.textViewContext.text = intent.getStringExtra("context")

        binding.textViewRecruited.text = intent.getStringExtra("recruited")
        var recruitedStr = intent.getStringExtra("recruited")
        if (recruitedStr != null && Integer.parseInt(recruitedStr) >= 4) {
            binding.endOrIng.text = "모집 완료"
        }
    }

    override fun onMapReady(naverMap: NaverMap) {
        this.naverMap = naverMap
        naverMap.locationSource = locationSource

        if (!arePermissionsGranted()) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, LOCATION_PERMISSTION_REQUEST_CODE)
        } else {
            naverMap.locationTrackingMode = LocationTrackingMode.Follow
            setMarker()
        }
    }

    fun setMarker() {
        val departure = intent.getStringExtra("departure")
        val destination = intent.getStringExtra("destination")

        for (markerInfo in markerList) {
            if (markerInfo[0] == departure) {
                val departurePosition = LatLng(markerInfo[1] as Double, markerInfo[2] as Double)
                val departureMarker = Marker()
                departureMarker.position = departurePosition
                departureMarker.iconTintColor = ContextCompat.getColor(this, R.color.myYellow)
                departureMarker.map = naverMap
                departureMarker.captionText = markerInfo[0] as String
            }

            if (markerInfo[0] == destination) {
                val destinationPosition = LatLng(markerInfo[1] as Double, markerInfo[2] as Double)
                val destinationMarker = Marker()
                destinationMarker.position = destinationPosition
                destinationMarker.iconTintColor = ContextCompat.getColor(this, R.color.myBurgundy02)
                destinationMarker.map = naverMap
                destinationMarker.captionText = markerInfo[0] as String
            }
        }

    /*
    * for (markerInfo in markerList) {
            if (markerInfo[0].equals(departureLocation)) {
                val position = LatLng(markerInfo[1] as Double, markerInfo[2] as Double)
                val departureMarker = Marker()
                departureMarker.position = position
                departureMarker.iconTintColor = ContextCompat.getColor(this, R.color.myYellow)
                departureMarker.map = naverMap
                departureMarker.captionText = markerInfo[0] as String
                break // 선택한 출발지 마커만 표시하고 루프 종료
            }
        }
    * */

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSTION_REQUEST_CODE) {
            if (arePermissionsGranted()) {
                naverMap.locationTrackingMode = LocationTrackingMode.Follow
            }
        }
    }

    private fun arePermissionsGranted(): Boolean {
        return PERMISSIONS.all {
            ContextCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED
        }
    }
    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }

    override fun onStop() {
        super.onStop()
        mapView.onStop()
    }



    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }
}
