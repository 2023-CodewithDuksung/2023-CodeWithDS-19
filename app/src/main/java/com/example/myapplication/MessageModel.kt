package com.example.myapplication

data class MessageModel(
    var chatId: String? = null, //채팅방 uid
    var userId: String? = null, //사용자 uid
    var message: String? = null, //대화 내용
    var date: String? = null, //0000-00-00
    var time: String? = null, //00:00
)
