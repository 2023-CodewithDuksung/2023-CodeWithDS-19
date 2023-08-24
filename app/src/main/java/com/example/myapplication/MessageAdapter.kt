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
            binding.sendTxt.text = data.message
        } else { //받는 데이터
            val binding = (holder as ReceiveViewHolder).binding

            val data_pre = itemList.get(position-1)
            val temp = data.time.split(":")[1].toInt() - data_pre.time.split(":")[1].toInt()

            val param: LinearLayout.LayoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )

            if (data.date != data_pre.date) {
                binding.date.text = data.date
                binding.dateBox.visibility = View.VISIBLE
            } else {
                binding.dateBox.visibility = View.GONE
            }

            if (data.chatId == data_pre.chatId && data.date == data_pre.date && temp < 10) {
                binding.time.text = data.time

                binding.userImg.visibility = View.VISIBLE
                binding.userName.visibility = View.VISIBLE
                binding.time.visibility = View.VISIBLE

                param.leftMargin = 0
                binding.receiveTxt.layoutParams = param
            } else {
                binding.userImg.visibility = View.GONE
                binding.userName.visibility = View.GONE
                binding.time.visibility = View.GONE

                param.leftMargin = 50
                binding.receiveTxt.layoutParams = param
            }
            binding.receiveTxt.text = data.message
        }
    }
}