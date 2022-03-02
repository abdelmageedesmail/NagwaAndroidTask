package com.abdelmageed.nagwaandroidtask.ui

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.abdelmageed.nagwaandroidtask.di.AppModule
import com.abdelmageed.nagwaandroidtask.di.DaggerAppComponent

class MainApp : MultiDexApplication() {

    @Override
    override fun attachBaseContext(base: Context?) {
        MultiDex.install(this)
        super.attachBaseContext(base)

    }

    val appComponent = DaggerAppComponent.builder().appModule(AppModule(this)).build()
}