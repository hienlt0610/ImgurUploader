package dev.hienlt.imguruploader

import android.app.Application
import com.androidnetworking.AndroidNetworking

class MyApp: Application(){
    override fun onCreate() {
        super.onCreate()
        AndroidNetworking.initialize(applicationContext)
    }
}
