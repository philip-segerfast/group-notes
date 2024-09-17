package org.robphi.groupnotes

import android.app.Application
import com.mmk.kmpauth.google.GoogleAuthCredentials
import com.mmk.kmpauth.google.GoogleAuthProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.robphi.groupnotes.di.appModule
import org.robphi.groupnotes.shared.BuildKonfig


private const val TAG = "Application"

class Application : Application() {

    private val scope = CoroutineScope(Dispatchers.Default)

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@Application)
            modules(appModule)
        }

        setGoogleAuthCredentials()
    }

    private fun setGoogleAuthCredentials() {
        val webClientId = BuildKonfig.WEB_CLIENT_ID
        GoogleAuthProvider.create(credentials = GoogleAuthCredentials(serverId = webClientId))
    }

}
