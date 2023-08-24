package com.example.myapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.NoticeItemBinding

//class TalkRecyclerViewAdapter(val context: Context, val itemList: MutableList<TalkModel>): RecyclerView.Adapter<TalkRecyclerViewAdapter.MyViewHolder>() {
//    //커스텀 리스너 인터페이스
//    interface OnItemClickListener {
//        fun onItemClick(v: View, position: Int)
//    }
//
//    //리스너 객체 참조 저장
//    var mListener: OnItemClickListener? = null
//
//    //리스너 객체 참조를 어댑터에 전달
//    fun setOnItemClickListener(listener: OnItemClickListener) {
//        mListener = listener
//    }
//
//    inner class MyViewHolder(val binding: ): RecyclerView.ViewHolder(binding.root) {
//        init {
//            itemView.setOnClickListener {
//                var pos = adapterPosition
//                if (pos != RecyclerView.NO_POSITION && mListener != null) {
//                    mListener?.onItemClick(it, pos)
//                }
//            }
//        }
//    }
//
//    override fun getItemCount(): Int {
//        return itemList.size
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TalkRecyclerViewAdapter.MyViewHolder
//        = MyViewHolder(.inflate(LayoutInflater.from(parent.context), parent, false))
//
//    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
//        val data = itemList.get(position)
//
//        holder.binding.run {
//
//        }
//    }
//}