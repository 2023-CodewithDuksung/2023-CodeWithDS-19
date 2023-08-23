package com.example.myapplication

data class NoticeModel(
    val title: String,
    val departure: String,
    val destination: String,
    val time: String,
    val deadline: String,
    val recruitment: Int,
    val recruited: Int,
    val host : String,
    val appliedMembers: MutableSet<String> = mutableSetOf() // 신청한 멤버들 목록
)



