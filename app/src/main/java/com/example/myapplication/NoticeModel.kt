package com.example.myapplication

data class NoticeModel(
    var docId: String? = null,
    val title: String? = null,
    val departure: String? = null,
    val destination: String? = null,
    val time: String? = null,
    val deadline: String? = null,
    val recruitment: Int? = 0,
    val recruited: Int? = 0,
    val host : String? = null,
    val taxiOrWalk : Int? = 0,
//    val appliedMembers: MutableSet<String> = mutableSetOf() // 신청한 멤버들 목록
    val user1 : String? = null,
    val user2 : String? = null,
    val user3 : String? = null,
)



