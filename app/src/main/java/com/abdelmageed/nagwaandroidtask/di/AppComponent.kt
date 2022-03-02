package com.abdelmageed.nagwaandroidtask.di

import com.abdelmageed.nagwaandroidtask.ui.activities.HomeActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    fun inject(homeActivity: HomeActivity)

}