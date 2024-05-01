package com.example.groupnotes

import android.app.Application
import android.util.Log
import com.fingerprintjs.android.fingerprint.Fingerprinter
import com.fingerprintjs.android.fingerprint.FingerprinterFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.util.Objects
import kotlin.random.Random

private const val TAG = "Application"

private val _apiRepository = ApiRepository()
val groupService = _apiRepository.groupService
val userService = _apiRepository.userService
//val noteService = _apiRepository.noteService

typealias DeviceId = Long

private val _deviceUserId = MutableStateFlow<DeviceId?>(null)
val deviceUserId = _deviceUserId.asStateFlow()

class Application : Application() {

    private val scope = CoroutineScope(Dispatchers.Default)

    override fun onCreate() {
        super.onCreate()

        val fingerPrinter = FingerprinterFactory.create(this)
        fingerPrinter.getDeviceId(version = Fingerprinter.Version.latest) {
            val hash = Objects.hash(it.deviceId)
            _deviceUserId.value = Random(hash).nextLong(0, 1000)
        }

        scope.launch {
            Log.d(TAG, "Waiting for user id...")
            val id = deviceUserId.first { it != null }!!
            Log.d(TAG, "User ID: $id. Waiting for user...")

            val user = userService.getUserById(id).user
            Log.d(TAG, "User: $user")


            val allUsers = userService.getAllUsers()
            Log.d(TAG, allUsers.users.joinToString(separator = "") { "* ${ it.toString() + System.lineSeparator() }" })
        }
    }

}
