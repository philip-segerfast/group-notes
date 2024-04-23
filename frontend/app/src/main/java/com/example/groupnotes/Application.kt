package com.example.groupnotes

import android.app.Application

private const val TAG = "Application"

private val apiRepository = ApiRepository()
val apiService = apiRepository.apiService

class Application : Application() {

    override fun onCreate() {
        super.onCreate()


    }

}
