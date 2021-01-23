package com.example.saitowapp

import android.app.Application
import timber.log.Timber

class SaitowApplication : Application(){
    override fun onCreate() {
        super.onCreate()
        if(BuildConfig.DEBUG){
            Timber.plant(Timber.DebugTree())
        }
    }
}