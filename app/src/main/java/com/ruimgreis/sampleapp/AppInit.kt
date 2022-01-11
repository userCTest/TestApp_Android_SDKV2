package com.ruimgreis.sampleapp

import android.app.Application
import android.content.Context
import com.usercentrics.sdk.Usercentrics
import com.usercentrics.sdk.UsercentricsOptions
import com.usercentrics.sdk.models.common.UsercentricsLoggerLevel
import com.ruimgreis.sampleapp.Init

class AppInit: Application() {

    /**
     * SDK
     */

    val initUC = Init()

    override fun onCreate() {
        super.onCreate()

        /**
         * calling SDK initialization
         */

        initUC.initCMP(applicationContext)
    }
}