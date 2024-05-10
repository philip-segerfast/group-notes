package org.robphi.groupnotes

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val TAG = "Application"

@HiltAndroidApp
class Application : Application() {

    private val scope = CoroutineScope(Dispatchers.Default)

    override fun onCreate() {
        super.onCreate()

        scope.launch {

        }
    }

}
