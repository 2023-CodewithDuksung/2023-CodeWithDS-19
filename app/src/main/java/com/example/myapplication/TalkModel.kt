package com.example.myapplication

data class TalkModel(
    var chatId: String, //채팅방 id
    var docId: String, //notice id
    var usersId: ArrayList<String>, //참여자 id 목록 ex) id - id - id ...
)