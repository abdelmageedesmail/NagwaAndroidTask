package com.abdelmageed.nagwaandroidtask.di

import android.app.Application
import android.content.Context
import com.abdelmageed.nagwaandroidtask.data.remote.ApiInterface
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class AppModule(private val application: Application) {


    @Provides
    @Singleton
    fun initApiService(): ApiInterface =
        Retrofit.Builder()
            .baseUrl("https://www.learningcontainer.com/wp-content/uploads/2020/05/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)

    @Provides
    @Singleton
    fun providesApplication(): Application = application

    @Provides
    @Singleton
    fun providesApplicationContext(): Context = application

}