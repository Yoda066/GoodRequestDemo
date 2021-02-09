package com.example.goodrequestdemo.reqres

data class RequresResponse(
    val data: List<User>,
    val page: Int,
    val per_page: Int,
    val total: Int,
    val total_pages: Int
)