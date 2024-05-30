package ru.mirea.computerclub.di

import android.content.Context
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module(includes = [NetworkModule::class])
class AndroidModule(private val context: Context) {

    @Provides
    @Singleton
    fun providePicasso(client: OkHttpClient): Picasso = Picasso.Builder(context)
        .downloader(OkHttp3Downloader(client))
        .build()

}