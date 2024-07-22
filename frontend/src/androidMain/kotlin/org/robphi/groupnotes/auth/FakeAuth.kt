package org.robphi.groupnotes.auth

import android.content.Context
import com.fingerprintjs.android.fingerprint.Fingerprinter
import com.fingerprintjs.android.fingerprint.FingerprinterFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Objects
import kotlin.random.Random

class FakeAuth(
    private val context: Context
) : Auth {

    private val _deviceUserId = MutableStateFlow<Long?>(null)
    override val userId: StateFlow<Long?> = _deviceUserId.asStateFlow()

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