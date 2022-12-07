package com.example.unittestingsamplekotlin

import kotlinx.coroutines.delay

class MainRepo {

    private val db = mutableMapOf<String, User>()

    fun save(user: User) = db.let { it[user.email] = user }

    fun get(key: String): User? = db[key]

    fun clear(key: String) = db.remove(key)

    fun clearAll() = db.clear()

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