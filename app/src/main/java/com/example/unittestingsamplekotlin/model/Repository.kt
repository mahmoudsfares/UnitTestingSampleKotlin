package com.example.unittestingsamplekotlin.model

import com.example.unittestingsamplekotlin.data.FakeResponse
import kotlinx.coroutines.delay

class Repository {

    suspend fun getClass(age: Int): FakeResponse {
        delay(1000)
        return if(age in 0..17) FakeResponse(200,"""
            {
            "error": null,
            "data": "minor"
            }
        """)
        else if(age in 18..30) FakeResponse(200,"""
            {
            "error": null,
            "data": "normal"
            }
        """)
        else if(age > 60) FakeResponse(200,"""
            {
            "error": null,
            "data": "senior"
            }
        """)
        else FakeResponse(400,"""
            {
            "error": "invalid age",
            "data": null
            }
        """)
    }
}