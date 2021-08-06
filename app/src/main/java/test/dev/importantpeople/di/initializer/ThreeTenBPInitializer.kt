package test.dev.importantpeople.di.initializer

import android.content.Context
import androidx.startup.Initializer
import com.jakewharton.threetenabp.AndroidThreeTen

class ThreeTenBPInitializer : Initializer<Unit> {
    override fun create(context: Context) {
        AndroidThreeTen.init(context)
    }

    override fun dependencies(): List<Class<out Initializer<*>>> = emptyList()
}