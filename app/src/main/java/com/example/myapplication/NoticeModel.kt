package com.example.myapplication

data class NoticeModel(
    var docId: String? = null,
    val title: String? = null,
    val departure: String? = null,
    val destination: String? = null,
    val currentDay: String? = null,
    val meetingTime : String? = null,

    val recruitment: String = "4",
    val recruited: String = "1",
    val host : String? = null,
    val context : String? = null,
    val taxiOrWalk : String? = null,

//    val appliedMembers: MutableSet<String> = mutableSetOf() // 신청한 멤버들 목록
    val chatId: String? = null,
    val usersId: ArrayList<String>? = null
)



