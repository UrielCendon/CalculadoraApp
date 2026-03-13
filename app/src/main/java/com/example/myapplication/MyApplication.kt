package com.example.myapplication

import android.app.Application
import android.util.Log

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Log.d("MyApplication", "La aplicacion se ha inicializado")
    }
}
