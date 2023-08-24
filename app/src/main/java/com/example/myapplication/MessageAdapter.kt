package com.example.myapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ReceiveMessageBinding
import com.example.myapplication.databinding.SendMessageBinding
import com.google.firebase.auth.FirebaseAuth

class ReceiveViewHolder(val binding: ReceiveMessageBinding): RecyclerView.ViewHolder(binding.root)
class SendViewHolder(val binding: SendMessageBinding): RecyclerView.ViewHolder(binding.root)

class MessageAdapter(val context: Context, val itemList: ArrayList<MessageModel>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val receive = 1 //받는 타입
    val send = 2 //보내는 타입

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if(viewType == 1) { //받는 화면
            val layoutInflater = LayoutInflater.from(parent.context)
            return ReceiveViewHolder(ReceiveMessageBinding.inflate(layoutInflater))
        } else { //보내는 화면
            val layoutInflater = LayoutInflater.from(parent.context)
            return SendViewHolder(SendMessageBinding.inflate(layoutInflater))
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun getItemViewType(position: Int): Int {
        val currentMessage = itemList[position]

        return if(FirebaseAuth.getInstance().currentUser?.uid.equals(currentMessage.userId)) {
            send
        } else {
            receive
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        //현재 메세지
        val data = itemList.get(position)

        //보내는 데이터
        if(holder.javaClass == SendViewHolder::class.java) {
            val binding = (holder as SendViewHolder).binding

            binding.date.text = data.date
            binding.dateBox.visibility = View.GONE
            binding.time.text = data.time
            binding.time.visibility = View.VISIBLE
            binding.sendTxt.text = data.message

        } else { //받는 데이터
            val binding = (holder as ReceiveViewHolder).binding

            binding.date.text = data.date
            binding.dateBox.visibility = View.GONE
            binding.time.text = data.time
            binding.userImg.visibility = View.VISIBLE
            binding.userName.visibility = View.VISIBLE
            binding.time.visibility = View.VISIBLE
            binding.receiveTxt.text = data.message
        }
    }
}