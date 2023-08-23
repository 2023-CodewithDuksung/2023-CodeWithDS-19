package com.example.myapplication

data class NoticeModel(
    var docId: String? = null,
    val title: String? = null,
    val departure: String? = null,
    val destination: String? = null,
    val currentDay: String? = null,
    val meetingTime : String? = null,

    val recruitment: String? = null,
    val recruited: String? = null,
    val host : String? = null,
    val context : String? = null,
    val taxiOrWalk : String? = null,

//    val appliedMembers: MutableSet<String> = mutableSetOf() // 신청한 멤버들 목록
    val user1 : String? = null,
    val user2 : String? = null,
    val user3 : String? = null,
)



