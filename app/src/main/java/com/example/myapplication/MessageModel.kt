package com.example.myapplication

data class MessageModel(
    var chatId: String, //채팅방 uid
    var userId: String, //사용자 uid
    var message: String, //대화 내용
    var date: String, //0000-00-00
    var time: String, //00:00
)
