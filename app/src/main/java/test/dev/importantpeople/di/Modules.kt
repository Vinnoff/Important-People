package test.dev.importantpeople.di

import com.facebook.stetho.okhttp3.StethoInterceptor
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import test.dev.importantpeople.BuildConfig
import test.dev.importantpeople.data.remote.RandomUserService
import test.dev.importantpeople.data.remote.source.RemoteDataSource
import test.dev.importantpeople.data.repository.UserRepository
import test.dev.importantpeople.data.repository.UserRepositoryImpl
import test.dev.importantpeople.domain.interactors.people.GetRandomUserListUseCase
import test.dev.importantpeople.presentation.user.UserViewModel
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

            single<UserRepository> { UserRepositoryImpl(get()) }
        }

        val domainModule = module {
            factory { GetRandomUserListUseCase(get()) }
        }

        val presentationModule = module {
            viewModel { UserViewModel(get(), get()) }
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