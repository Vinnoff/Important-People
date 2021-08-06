package test.dev.importantpeople.di.initializer

import android.content.Context
import androidx.startup.Initializer
import com.facebook.stetho.Stetho
import test.dev.importantpeople.BuildConfig
import timber.log.Timber

class LoggerInitializer : Initializer<Unit> {
    override fun create(context: Context) {
        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(context)
            Timber.uprootAll()
            Timber.plant(Timber.DebugTree())
        } else {
            Timber.uprootAll()
            Timber.plant(object : Timber.Tree() {
                override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
                    return
                }
            })
        }
    }

    override fun dependencies(): List<Class<out Initializer<*>>> = emptyList()
}