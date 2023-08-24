package com.example.myapplication

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TimePicker
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.myapplication.MyApplication.Companion.auth
import com.example.myapplication.databinding.ActivityFormBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.LocationTrackingMode
import com.naver.maps.map.MapView
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.util.FusedLocationSource
import java.text.SimpleDateFormat
import java.util.*


class FormActivity : AppCompatActivity(), OnMapReadyCallback {
    lateinit var binding: ActivityFormBinding

    private lateinit var mapView: MapView
    private lateinit var naverMap: NaverMap
    private val LOCATION_PERMISSTION_REQUEST_CODE: Int = 1000
    private lateinit var locationSource: FusedLocationSource // 위치를 반환하는 구현체

    //지도 마커
    private  var departureLocation : String =""
    private  var destinationLocation : String =""

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
        binding =ActivityFormBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        // view binding을 사용하여 레이아웃 파일 설정


        setContentView(binding.root)
        // 택시 or 도보 intent로 데이터 받아와야함
        //val taxiOrWalk : Int = intent.getStringExtra()

        locationSource = FusedLocationSource(this, LOCATION_PERMISSTION_REQUEST_CODE)


        val taxiOrWalk = intent.getStringExtra("taxiOrWalk")
        binding.textViewTaxiOrWalk.text = taxiOrWalk


        //mapView
        mapView = binding.navermap
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)

        binding.saveBtn.setOnClickListener {
            if(MyApplication.checkAuth()){ // 예외처리
                saveStore()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "제목을 입력해주세요..", Toast.LENGTH_SHORT).show()
            }
            finish()
        }
        binding.textViewDeparture.setOnClickListener {
            showBottomSheet("departure")
            //setMarker()
        }
        binding.textViewDestination.setOnClickListener {
            showBottomSheet("destination")
            //setMarker()
        }

        binding.textViewMeetingTime.setOnClickListener {
            showBottomSheet("meetingTime")
        }


    }

    override fun onMapReady(naverMap: NaverMap){
        this.naverMap = naverMap


        naverMap.locationSource = locationSource
        naverMap.locationTrackingMode = LocationTrackingMode.Follow

//        setMarker()

    }

    fun setMarker() {
        // 이전에 표시한 마커들 삭제


        // 선택한 출발지 마커 표시
        if (departureLocation != "") {
            for (markerInfo in markerList) {
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
        }

        // 선택한 목적지 마커 표시
        if (destinationLocation != "") {
            for (markerInfo in markerList) {
                if (markerInfo[0].equals(destinationLocation)) {
                    val position = LatLng(markerInfo[1] as Double, markerInfo[2] as Double)
                    val destinationMarker = Marker()
                    destinationMarker.position = position
                    destinationMarker.iconTintColor = ContextCompat.getColor(this, R.color.myBurgundy03)
                    destinationMarker.map = naverMap
                    destinationMarker.captionText = markerInfo[0] as String
                    break // 선택한 목적지 마커만 표시하고 루프 종료
                }
            }
        }
    }



    private fun showBottomSheet(str : String) {
        val bottomSheetDialog = BottomSheetDialog(this)
        lateinit var bottomSheetView: View // View 타입으로 명시
        when (str) {
            "departure" -> {

                bottomSheetView = layoutInflater.inflate(R.layout.list_departure, null)
                val departureButtons = listOf(
                    bottomSheetView.findViewById<Button>(R.id.btnDeparture1),
                    bottomSheetView.findViewById<Button>(R.id.btnDeparture2),
                    bottomSheetView.findViewById<Button>(R.id.btnDeparture3),
                    bottomSheetView.findViewById<Button>(R.id.btnDeparture4),
                    bottomSheetView.findViewById<Button>(R.id.btnDeparture5),
                    bottomSheetView.findViewById<Button>(R.id.btnDeparture6),
                    bottomSheetView.findViewById<Button>(R.id.btnDeparture7),
                    bottomSheetView.findViewById<Button>(R.id.btnDeparture8)
                )
                val listener = View.OnClickListener { view ->
                    val selectedValue = (view as Button).text.toString()
                    binding.textViewDeparture.text = selectedValue
                    departureLocation = selectedValue
                    setMarker()
                    bottomSheetDialog.dismiss() // 선택 후 바텀 시트 닫기

                }

                departureButtons.forEach { button ->
                    button.setOnClickListener(listener)
                }

            }
            "destination" -> {
                bottomSheetView = layoutInflater.inflate(R.layout.list_destination, null)

                val destinationButtons = listOf(
                    bottomSheetView.findViewById<Button>(R.id.btnDestination1),
                    bottomSheetView.findViewById<Button>(R.id.btnDestination2),
                    bottomSheetView.findViewById<Button>(R.id.btnDestination3),
                    bottomSheetView.findViewById<Button>(R.id.btnDestination4),
                    bottomSheetView.findViewById<Button>(R.id.btnDestination5),
                    bottomSheetView.findViewById<Button>(R.id.btnDestination6),
                    bottomSheetView.findViewById<Button>(R.id.btnDestination7),
                    bottomSheetView.findViewById<Button>(R.id.btnDestination8)
                )
                val listener = View.OnClickListener { view ->
                    val selectedValue = (view as Button).text.toString()
                    binding.textViewDestination.text = selectedValue
                    destinationLocation=selectedValue
                    setMarker()
                    bottomSheetDialog.dismiss() // 선택 후 바텀 시트 닫기

                }

                destinationButtons.forEach { button ->
                    button.setOnClickListener(listener)
                }

            }"meetingTime" -> {
            bottomSheetView = layoutInflater.inflate(R.layout.list_meeting_time, null)
            val timePicker = bottomSheetView.findViewById<TimePicker>(R.id.timePicker)
            timePicker.setOnTimeChangedListener { _, hourOfDay, minute ->
                val selectedTime = String.format("%02d:%02d", hourOfDay, minute)
                binding.textViewMeetingTime.text = selectedTime
                Toast.makeText(baseContext, "${selectedTime}", Toast.LENGTH_SHORT).show()
            }
        }

            else -> {
                // 기본적인 처리나 예외 처리를 수행합니다.
            }
        }



        bottomSheetDialog.setContentView(bottomSheetView)
        bottomSheetDialog.show()
    }
    fun dateToString(date: Date): String {
        val format = SimpleDateFormat("yyyy-MM-dd", Locale.KOREAN)
        return format.format(date)
    }

    fun saveStore() {
        val data = mapOf(
            "title" to binding.textViewTitle.text.toString(),
            "host" to MyApplication.email,
            "departure" to binding.textViewDeparture.text.toString(),
            "destination" to binding.textViewDestination.text.toString(),
            "currentDay" to dateToString(Date()),
            "meetingTime" to binding.textViewMeetingTime.text.toString(),// 이거 포멧해야함

            "context" to binding.textViewContext.text.toString(),
            "taxiOrWalk" to binding.textViewTaxiOrWalk.text.toString(),

            )

        MyApplication.db.collection("notices")
            .add(data)
            .addOnSuccessListener {
                Log.d("ToyProject", "data firestore save ok")
//              uploadImage(it.id)
            }
            .addOnFailureListener {
                Log.d("ToyProject", "data firestore save error")
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

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

}