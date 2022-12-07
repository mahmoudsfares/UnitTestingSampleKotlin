package com.example.unittestingsamplekotlin

import com.beust.klaxon.Klaxon
import com.beust.klaxon.KlaxonException
import com.example.unittestingsamplekotlin.data.FakeBody
import com.example.unittestingsamplekotlin.model.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException

class MainViewModel constructor(private val repo: Repository) {

    suspend fun getClassNormally(age: Int): String? {
        try {
            val response = repo.getClass(age)
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

    suspend fun getClassFlow(age: Int): Flow<String?> {
        return flow {
            emit("Loading..")
            try {
                val response = repo.getClass(age)
                when (response.responseCode) {
                    200 -> {
                        emit(Klaxon().parse<FakeBody>(response.jsonBody)!!.data)
                    }
                    400 -> {
                        emit(Klaxon().parse<FakeBody>(response.jsonBody)!!.error)
                    }
                    else -> {
                        emit("Unexpected error")
                    }
                }
            } catch (throwable: Throwable) {
                // klaxon exception occurs when a parsing error happens
                // but it also extends RuntimeException. This was noticed when the unit tests were run
                if (throwable is java.lang.RuntimeException && throwable !is KlaxonException){
                    emit("Server error")
                } else if (throwable is IOException){
                    emit("No internet connection")
                } else {
                    emit("Unexpected error")
                }
            }
        }
    }
}