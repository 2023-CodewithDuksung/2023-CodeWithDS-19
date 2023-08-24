package com.example.myapplication

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.NoticeItemBinding

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

    inner class MyFeedViewHolder(val binding: NoticeItemBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener {
                val pos = adapterPosition
                if (pos != RecyclerView.NO_POSITION && mListener != null) {
                    mListener?.onItemClick(it, pos)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyFeedViewHolder
            = MyFeedViewHolder(NoticeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: MyFeedViewHolder, position : Int) {
        val data = itemList.get(position)

        holder.binding.run {
            textViewTitle.text = data.title
            textViewDeparture.text=data.departure
            textViewDestination.text=data.destination
            textViewTaxiOrWalk.text=data.taxiOrWalk
            textViewTime.text = data.currentDay
            textViewDeadline.text=data.meetingTime
            textViewRecruited.text=data.recruited.toString()
            textViewRecruitment.text=data.recruitment.toString()

        }
    }
}