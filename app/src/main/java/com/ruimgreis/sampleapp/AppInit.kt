package com.ruimgreis.sampleapp

import android.app.Application

class AppInit: Application() {

    override fun onCreate() {
        super.onCreate()

        /**
         * calling SDK initialization
         */

        initCMP(applicationContext)
    }
}