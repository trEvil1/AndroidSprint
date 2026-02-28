package com.example.androidsprint

import android.app.Application
import com.example.androidsprint.di.AppContainer

class RecipeApplication: Application() {

    lateinit var appContainer: AppContainer

    override fun onCreate() {
        super.onCreate()

        appContainer = AppContainer(this)
    }
}