package test.dev.importantpeople

import android.app.Application
import org.koin.core.KoinApplication

class IPApplication  : Application() {

    companion object {
        lateinit var koinApp: KoinApplication
    }
}