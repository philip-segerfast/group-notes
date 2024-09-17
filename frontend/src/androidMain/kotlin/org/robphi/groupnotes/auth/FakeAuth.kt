package org.robphi.groupnotes.auth

import android.content.Context
import com.fingerprintjs.android.fingerprint.Fingerprinter
import com.fingerprintjs.android.fingerprint.FingerprinterFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.koin.core.KoinApplication.Companion.init
import org.robphi.groupnotes.api.UserId
import java.util.Objects
import kotlin.random.Random

class FakeAuth(
    private val context: Context
) : Auth {

    private val _deviceUserId = MutableStateFlow<Long?>(null)
    override val userId: StateFlow<Long?> = _deviceUserId.asStateFlow()

    override fun getUserIdBlocking(): UserId = runBlocking {
        userId.first { it != null }!!
    }

    override suspend fun getUserId(): UserId = userId.first { it != null }!!

    init {
        fetchDeviceId()
    }

    private fun fetchDeviceId() {
        val fingerPrinter = FingerprinterFactory.create(context)
        fingerPrinter.getDeviceId(version = Fingerprinter.Version.latest) {
            val hash = Objects.hash(it.deviceId)
            _deviceUserId.value = Random(hash).nextLong(0, 1000)
        }
    }
}