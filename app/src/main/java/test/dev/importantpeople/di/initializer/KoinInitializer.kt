package test.dev.importantpeople.di.initializer

import android.content.Context
import androidx.startup.Initializer
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import test.dev.importantpeople.BuildConfig
import test.dev.importantpeople.IPApplication
import test.dev.importantpeople.di.Modules.Companion.rootModule
import test.dev.importantpeople.di.Modules.Companion.dataModule
import test.dev.importantpeople.di.Modules.Companion.domainModule
import test.dev.importantpeople.di.Modules.Companion.presentationModule

class KoinInitializer : Initializer<KoinApplication> {
    override fun create(context: Context): KoinApplication {
        return startKoin {
            androidContext(context)
            modules(
                listOf(
                    rootModule,
                    dataModule,
                    domainModule,
                    presentationModule
                )
            )
            if (BuildConfig.DEBUG) androidLogger()
        }.also { koinApplication -> IPApplication.koinApp = koinApplication }
    }

    override fun dependencies(): List<Class<out Initializer<*>>> = listOf(LoggerInitializer::class.java)
}