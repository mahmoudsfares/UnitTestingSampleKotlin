package com.example.unittestingsamplekotlin

import com.beust.klaxon.Json

data class FakeResponse(val responseCode: Int, val jsonBody: String)

data class FakeBody(
    @Json(name = "error")
    val error: String?,
    @Json(name = "data")
    val data: String?
)
