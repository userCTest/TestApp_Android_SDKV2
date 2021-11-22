package com.ruimgreis.sampleapp

import android.app.Application
import com.usercentrics.sdk.Usercentrics
import com.usercentrics.sdk.UsercentricsOptions
import com.usercentrics.sdk.models.common.UsercentricsLoggerLevel

class AppInit: Application() {

    private var settingsId: String = "tSWIJ-nJ8"//"lrekP5cHl" //"ZDQes7xES", "egLMgjg9j"

    override fun onCreate() {
        super.onCreate()
        initCMP(settingsId)
    }

    private fun initCMP(settingsId: String) {
        val userOptions = UsercentricsOptions(
            settingsId = settingsId,
            defaultLanguage = "en",
            version = "latest",
            loggerLevel = UsercentricsLoggerLevel.DEBUG
        )

        Usercentrics.initialize(this, userOptions)
    }
}