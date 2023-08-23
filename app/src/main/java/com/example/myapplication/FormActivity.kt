package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TimePicker
import android.widget.Toast
import com.example.myapplication.MyApplication.Companion.auth
import com.example.myapplication.databinding.ActivityFormBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.*


class FormActivity : AppCompatActivity() {
    lateinit var binding: ActivityFormBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding =ActivityFormBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        // view binding을 사용하여 레이아웃 파일 설정


        setContentView(binding.root)
        // 택시 or 도보 intent로 데이터 받아와야함
        //val taxiOrWalk : Int = intent.getStringExtra()
        binding.saveBtn.setOnClickListener {
            if(MyApplication.checkAuth()){ // 예외처리
                saveStore()
            } else {
                Toast.makeText(this, "제목을 입력해주세요..", Toast.LENGTH_SHORT).show()
            }
            finish()
        }
        binding.textViewDeparture.setOnClickListener {
            showBottomSheet("departure")
        }
        binding.textViewDestination.setOnClickListener {
            showBottomSheet("destination")
        }
        binding.textViewRecruitment.setOnClickListener {
            showBottomSheet("reqruitment")
        }
        binding.textViewMeetingTime.setOnClickListener {
            showBottomSheet("meetingTime")
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
                    bottomSheetView.findViewById<Button>(R.id.btnDeparture6)
                )
                val listener = View.OnClickListener { view ->
                    val selectedValue = (view as Button).text.toString()
                    binding.textViewDeparture.text = selectedValue
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
                    bottomSheetView.findViewById<Button>(R.id.btnDestination6)
                )
                val listener = View.OnClickListener { view ->
                    val selectedValue = (view as Button).text.toString()
                    binding.textViewDestination.text = selectedValue
                    bottomSheetDialog.dismiss() // 선택 후 바텀 시트 닫기
                }

                destinationButtons.forEach { button ->
                    button.setOnClickListener(listener)
                }

            }
            "reqruitment" -> {
                bottomSheetView = layoutInflater.inflate(R.layout.list_recruitment, null)
                val reqruitmentButtons = listOf(
                    bottomSheetView.findViewById<Button>(R.id.btnRecruitment1),
                    bottomSheetView.findViewById<Button>(R.id.btnRecruitment2),
                    bottomSheetView.findViewById<Button>(R.id.btnRecruitment3),
                    bottomSheetView.findViewById<Button>(R.id.btnRecruitment4),
                )
                val listener = View.OnClickListener { view ->
                    val selectedValue = (view as Button).text.toString()
                    binding.textViewRecruitment.text = selectedValue
                    bottomSheetDialog.dismiss() // 선택 후 바텀 시트 닫기
                }

                reqruitmentButtons.forEach { button ->
                    button.setOnClickListener(listener)
                }
            }
            "meetingTime" -> {
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
            "recruitment" to binding.textViewRecruitment.text.toString(),
            "recruited" to binding.textViewRecruited.text.toString(),
            "context" to binding.textViewContext.text.toString(),
            "taxiOrWalk" to binding.textViewTaxiOrWalk.text.toString(),

        )

        MyApplication.db.collection("notices")
            .add(data)
            .addOnSuccessListener {
                Log.d("ToyProject", "data firestore save ok")
//                uploadImage(it.id)
            }
            .addOnFailureListener {
                Log.d("ToyProject", "data firestore save error")
            }
    }
}