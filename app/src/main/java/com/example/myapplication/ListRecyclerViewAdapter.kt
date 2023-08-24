package com.example.myapplication

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ListRecyclerviewBinding
import com.example.myapplication.databinding.NoticeItemBinding
import java.time.LocalDateTime
import java.time.ZoneId

class ListRecyclerViewAdapter (val context: Context, val itemList: MutableList<NoticeModel>): RecyclerView.Adapter<ListRecyclerViewAdapter.MyFeedViewHolder>() {

    //커스텀 리스너 인터페이스
    interface OnItemClickListener {
        fun onItemClick(v: View, position: Int)
    }

    //리스너 객체 참조 저장
    var mListener: OnItemClickListener? = null

    //리스너 객체 참조를 어댑터에 전달
    fun setOnItemClickListener(listener: OnItemClickListener) {
        mListener = listener
    }

//    inner class MyFeedViewHolder(val binding: NoticeItemBinding) : RecyclerView.ViewHolder(binding.root) {
    inner class MyFeedViewHolder(val binding: ListRecyclerviewBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener {
                var pos = adapterPosition
                if (pos != RecyclerView.NO_POSITION && mListener != null) {
                    mListener?.onItemClick(it, pos)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListRecyclerViewAdapter.MyFeedViewHolder
            = MyFeedViewHolder(ListRecyclerviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListRecyclerViewAdapter.MyFeedViewHolder
//            = MyFeedViewHolder(NoticeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ListRecyclerViewAdapter.MyFeedViewHolder, position : Int) {
        val data = itemList.get(position)

        holder.binding.run {
//            textViewTitle.text = data.title
//            textViewDeparture.text=data.departure
//            textViewDestination.text=data.destination
//            textViewTaxiOrWalk.text=data.taxiOrWalk
//            textViewTime.text = data.currentDay
//            textViewDeadline.text=data.meetingTime
//            textViewRecruited.text=data.recruited.toString()
//            textViewRecruitment.text=data.recruitment.toString()
/*
*     var docId: String? = null,
    val title: String? = null,
    val departure: String? = null,
    val destination: String? = null,
    val currentDay: String? = null,
    val meetingTime : String? = null,

    val recruitment: String? = "4",
    val recruited: String? = null,
    val host : String? = null,
    val context : String? = null,
    val taxiOrWalk : String? = null,

//    val appliedMembers: MutableSet<String> = mutableSetOf() // 신청한 멤버들 목록
    val user1 : String? = null,
    val user2 : String? = null,
    val user3 : String? = null,
* */
// NoticeDetailActiviety로 데이터 전달
            place.setOnClickListener {
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

                Intent(context, NoticeDetailActivity::class.java).apply {
                    putExtras(bundle)
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }.run { context.startActivity(this) }
            }

            fun setDate(): String {
                val now = LocalDateTime.now(ZoneId.of("Asia/Seoul"))
                val temp1 = data.currentDay!!.split("-")

                var txt: String = data.currentDay + " "

                if (now.year == temp1[0].toInt() && now.monthValue == temp1[1].toInt()) {
                    val temp3 = now.dayOfMonth - temp1[2].toInt()
                    if (temp3 == 0) { txt = "오늘 " }
                    else if (temp3 == 1) { txt = "어제 " }
                    else if (temp3 == -1) { txt = "내일 " }
                }

                txt += data.meetingTime

                return txt
            }

            //장소
            place.text = data.departure + " -> " + data.destination

            //날짜 변환 - 오늘, 어제, 내일, 날짜
            date.text = setDate()
            // email
            textViewHostEmail.text = data.host

            //택시 or 도보 이미지 변경
            if (data.taxiOrWalk == "taxi") { taxiOrWalk.setImageResource(R.drawable.by_texi) }
            else { taxiOrWalk.setImageResource(R.drawable.by_walk) }

            //모집중, 모집임박, 종료 상태
            //val temp = data.recruitment!!.toInt() - data.recruited!!.toInt()
            val temp3 = 1
            if (temp3 == 0) { recruitStr.text = "종료" }
            else if (temp3 == 1 && data.recruited!= null && Integer.parseInt(data.recruited) <4 ) { recruitStr.text = "모집임박" }
            else { recruitStr.text = "종료" }

            //모집인원, 모집정원
            recruit.text = data.recruited + "/" + data.recruitment
        }
    }
}