package ru.mirea.computerclub.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import me.sianaki.flowretrofitadapter.FlowCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit.Builder
import retrofit2.converter.gson.GsonConverterFactory
import ru.kasperskiy.translater.data.network.SkyengApi
import ru.kasperskiy.translater.data.network.retrofit.buildApi
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    fun provideGson(): Gson = GsonBuilder().setLenient().create()

    @Provides
    fun provideClient() = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .addNetworkInterceptor { chain ->
            chain.proceed(
                chain.request().newBuilder()
                    .header("accept", "application/json")
                    .build()
            )
        }
        .readTimeout(10, TimeUnit.SECONDS)
        .build()

    @Singleton
    @Provides
    fun provideApi(client: OkHttpClient, gson: Gson) = Builder()
        .client(client)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(FlowCallAdapterFactory.create())


    @Singleton
    @Provides
    fun provideTranslationApi(builder: Builder) = builder.buildApi<SkyengApi>()


}