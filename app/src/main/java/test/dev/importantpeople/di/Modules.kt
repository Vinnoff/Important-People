package test.dev.importantpeople.di

import android.content.Context
import android.os.Build.VERSION.SDK_INT
import coil.ImageLoader
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import com.facebook.stetho.okhttp3.StethoInterceptor
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import test.dev.importantpeople.BuildConfig
import test.dev.importantpeople.data.remote.RandomUserService
import test.dev.importantpeople.data.remote.source.RemoteDataSource
import test.dev.importantpeople.data.repository.UserRepository
import test.dev.importantpeople.data.repository.UserRepositoryImpl
import timber.log.Timber
import java.util.concurrent.TimeUnit

class Modules {
    companion object {
        val rootModule = module {
            single<CoroutineDispatcher> { Dispatchers.Main }
            single { createOkHttpClient() }
        }

        val dataModule = module {
            single { RemoteDataSource(get()) }

            single<RandomUserService> { createRandomPeopleWebService(get()) }
        }

        val domainModule = module {

        }

        val presentationModule = module {

        }
    }
}

fun createOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
    .connectTimeout(10, TimeUnit.SECONDS)
    .readTimeout(10, TimeUnit.SECONDS)
    .addNetworkInterceptor(StethoInterceptor())
    .addNetworkInterceptor(HttpLoggingInterceptor { message -> Timber.tag("okhttp").d(message) }.apply { level = HttpLoggingInterceptor.Level.BODY })
    .build()

inline fun <reified T> createRandomPeopleWebService(okHttpClient: OkHttpClient): T {
    return Retrofit.Builder()
        .baseUrl(BuildConfig.HOST)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create()
}