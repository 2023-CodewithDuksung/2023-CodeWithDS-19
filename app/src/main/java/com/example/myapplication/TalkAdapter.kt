package com.example.myapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TalkAdapter(private val context: Context, private val talkList: ArrayList<Talk>):
RecyclerView.Adapter<TalkAdapter.TalkViewHolder>(){



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TalkAdapter.TalkViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.list_talk, parent, false)

        return TalkViewHolder(view)
    }

    override fun onBindViewHolder(holder: TalkAdapter.TalkViewHolder, position: Int) {
        val currentTalk = talkList[position]
        holder.tvTitle.text = currentTalk.title

    }

    override fun getItemCount(): Int {
        return talkList.size
    }

    class TalkViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val tvTitle: TextView = itemView.findViewById(R.id.tv_title)
        //val tvMsg: TextView = itemView.findViewById(R.id.tv_msg)
    }
}