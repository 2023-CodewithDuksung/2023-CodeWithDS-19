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

class MyFeedViewHolder(val binding: NoticeItemBinding) : RecyclerView.ViewHolder(binding.root)

class ListRecyclerViewAdapter (val context: Context, val itemList: MutableList<NoticeModel>): RecyclerView.Adapter<MyFeedViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyFeedViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return MyFeedViewHolder(NoticeItemBinding.inflate(layoutInflater))
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: MyFeedViewHolder, position : Int) {
        val data = itemList.get(position)

        holder.binding.run {

            textViewTitle.text = data.title
            textViewDeparture.text=data.departure
            textViewDestination.text=data.destination
            textViewTime.text = data.time
            textViewDeadline.text=data.deadline
            textViewRecruited.text=data.recruited.toString()
            textViewRecruitment.text=data.recruitment.toString()

        }
    }
}