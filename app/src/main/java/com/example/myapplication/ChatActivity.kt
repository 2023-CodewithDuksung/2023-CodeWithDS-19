package com.example.myapplication

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.databinding.ActivityChatBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.gun0912.tedpermission.provider.TedPermissionProvider.context
import java.time.LocalDateTime
import java.time.ZoneId

class ChatActivity : AppCompatActivity() {

    lateinit var binding: ActivityChatBinding

    lateinit var mAuth: FirebaseAuth //인증객체
    lateinit var mDbRef: DatabaseReference //DB 객체

    lateinit var myId: String //접속자 id
    lateinit var myChatId: String //채팅방 id

    lateinit var messageList: ArrayList<MessageModel> //메세지리스트

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mAuth = FirebaseAuth.getInstance()
        mDbRef = FirebaseDatabase.getInstance().reference

        //초기화
        messageList = ArrayList()
        val messageAdapter = MessageAdapter(this, messageList)

        //RecyclerView
        binding.chatRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.chatRecyclerView.adapter = messageAdapter

        //접속자 uid
        myId = mAuth.currentUser!!.uid

        //채팅방 id
        myChatId = intent.getStringExtra("chatId").toString()

        //toolbar
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //메세지 전송 버튼 이벤트
        binding.sendBtn.setOnClickListener {
            Log.d("ToyProject", "send button click")

            val now = LocalDateTime.now(ZoneId.of("Asia/Seoul"))
            val date = now.year.toString() + "-" + now.monthValue.toString() + "-" + now.dayOfMonth.toString()
            val time = now.hour.toString() + ":" + now.minute.toString()

            val message = binding.messageEdit.text.toString()
            val messageObject = MessageModel(myChatId, myId, message, date, time)

            //데이터 저장
            mDbRef.child("chats").child(myChatId).child("message").push()
                .setValue(messageObject)
                .addOnSuccessListener {
                    Log.d("ToyPrjoect", "chat submit successed")
                }

            //입력값 초기화
            binding.messageEdit.setText("")
        }

        //메세지 가져오기
        mDbRef.child("chats").child(myChatId).child("message")
            .addValueEventListener(object: ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    messageList.clear()

                    for(postSnapshot in snapshot.children) {
                        val message = postSnapshot.getValue(MessageModel::class.java)
                        messageList.add(message!!)
                    }
                    //적용
                    messageAdapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}