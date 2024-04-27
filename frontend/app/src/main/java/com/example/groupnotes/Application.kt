package com.example.groupnotes

import android.app.Application

private const val TAG = "Application"

private val _apiRepository = ApiRepository()
val apiRepository = _apiRepository.apiService

class Application : Application() {

    override fun onCreate() {
        super.onCreate()


    }

}
