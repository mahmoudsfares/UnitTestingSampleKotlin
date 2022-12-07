package com.example.unittestingsamplekotlin

import com.beust.klaxon.Klaxon
import com.beust.klaxon.KlaxonException
import java.io.IOException

class MainViewModel constructor(
    private val repo: MainRepo
) {
    suspend fun saveUser(user: User) {
        repo.save(user)
    }

    suspend fun getUser(id: String): User {
        return repo.get(id) ?: throw IllegalArgumentException("User with id $id not found")
    }

    suspend fun deleteUser(id: String) {
        repo.clear(id)
    }

    fun clearAll() = repo.clearAll()

    suspend fun getClass(id: Int): String? {
        try {
            val response = repo.getClass(id)
            return when (response.responseCode) {
                200 -> {
                    Klaxon().parse<FakeBody>(response.jsonBody)!!.data
                }
                400 -> {
                    Klaxon().parse<FakeBody>(response.jsonBody)!!.error
                }
                else -> {
                    "Unexpected error"
                }
            }
        }  catch (throwable: Throwable) {
            // klaxon exception occurs when a parsing error happens
            // but it also extends RuntimeException. This was noticed when the unit tests were run
            return if (throwable is java.lang.RuntimeException && throwable !is KlaxonException){
                "Server error"
            } else if (throwable is IOException){
                "No internet connection"
            } else {
                "Unexpected error"
            }
        }
    }
}