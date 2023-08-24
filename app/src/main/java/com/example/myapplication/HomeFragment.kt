package com.example.myapplication

import android.content.ClipData
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.MyApplication.Companion.db
import com.example.myapplication.databinding.FragmentHomeBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.Overlay
import com.naver.maps.map.util.FusedLocationSource
import com.naver.maps.map.util.MarkerIcons


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
class HomeFragment : Fragment(), OnMapReadyCallback {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var binding: FragmentHomeBinding

    //현 위치
    private lateinit var mapView: MapView
    private val LOCATION_PERMISSTION_REQUEST_CODE: Int = 1000
    private lateinit var locationSource: FusedLocationSource // 위치를 반환하는 구현체
    private lateinit var naverMap: NaverMap
    //// 마커 리스트
    //lateinit var markerList: List<NoticeModel>
/*
*         덕성여대 후문: 37.652135, 127.018054
                덕성여대 정문: 37.652933, 127.016745
    가오리역: 37.641224, 127.016088
    4.19역: 37.649595, 127.017725
    수유역: 37.637105, 127.024856
    쌍문역: 37.648087, 127.034662
* */

    val markerList = arrayOf(
        listOf("덕성여대 정문", 37.652933, 127.016745),
        listOf("덕성여대 후문", 37.652135, 127.018054),
        listOf("가오리역", 37.641224, 127.016088),
        listOf("4.19역", 37.649595, 127.017725),
        listOf("수유역", 37.637105, 127.024856),
        listOf("쌍문역", 37.648087, 127.034662),
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //현 위치
        locationSource = FusedLocationSource(this, LOCATION_PERMISSTION_REQUEST_CODE)

        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val rootView = inflater.inflate(R.layout.fragment_home, container, false)

        mapView = rootView.findViewById(R.id.map)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)

        locationSource = FusedLocationSource(requireActivity(), LOCATION_PERMISSTION_REQUEST_CODE)

        return rootView
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // onViewCreated 내용 추가
    }

    //현 위치
    override fun onMapReady(@NonNull naverMap: NaverMap) {
        this.naverMap = naverMap
        naverMap.locationSource = locationSource
        naverMap.locationTrackingMode = LocationTrackingMode.Follow

        setMarker()

    }


    fun setMarker() {

        for (markerInfo in markerList) {
            val marker = Marker()
            val position = LatLng(markerInfo[1] as Double, markerInfo[2] as Double)
            marker.position = position
            marker.map = naverMap
            marker.captionText = markerInfo[0] as String
            marker.setOnClickListener { onClick(marker) } // 마커 클릭 이벤트 설정

        }
    }
    fun onClick(@NonNull overlay: Overlay): Boolean {
        if (overlay is Marker) {
            Toast.makeText(requireContext(), "${overlay.captionText}마커가 선택되었습니다.", Toast.LENGTH_LONG).show()
            showBottomSheet(overlay.captionText)
            return true
        }
        return false
    }


    private fun showBottomSheet(departure: String) {
        val bottomSheetDialog = BottomSheetDialog(requireContext())
        val bottomSheetView = layoutInflater.inflate(R.layout.list_home_bottom_list, null)

        // 리사이클러뷰 초기화 및 어댑터 설정
        val recyclerView = bottomSheetView.findViewById<RecyclerView>(R.id.bottomRecyclerView)
        MyApplication.db.collection("notices")
            //.orderBy("date", Query.Direction.DESCENDING)
            .whereEqualTo("departure", departure)
            .get()
            .addOnSuccessListener { result ->
                val itemList = mutableListOf<NoticeModel>()
                for(document in result){
                    val item = document.toObject(NoticeModel::class.java)
                    item.docId = document.id
                    itemList.add(item)
                }
                recyclerView.layoutManager = LinearLayoutManager(requireContext())
                recyclerView.adapter = ListRecyclerViewAdapter(requireContext(), itemList)
                Log.d("ToyProject", "${itemList}")


            }
            .addOnFailureListener{
                Toast.makeText(requireContext(), "데이터 획득 실패", Toast.LENGTH_SHORT).show()
            }

        bottomSheetDialog.setContentView(bottomSheetView)
        bottomSheetDialog.show()
    }






    //test
    fun getFirebaseList() {
        val itemsCollection = db.collection("notices")
//        markerList = List<NoticeModel>()
        itemsCollection
            //.whereEqualTo("departure", targetDeparture)
            .get()
            .addOnSuccessListener { querySnapshot ->

                // itemList에 데이터가 담김
            }
            .addOnFailureListener { exception ->
                // 데이터 가져오기 실패 처리
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

    override fun onDestroyView() {
        super.onDestroyView()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }


}