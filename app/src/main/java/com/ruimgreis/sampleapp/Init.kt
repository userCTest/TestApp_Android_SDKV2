package com.ruimgreis.sampleapp

import android.app.Application
import android.content.Context
import com.usercentrics.sdk.Usercentrics
import com.usercentrics.sdk.UsercentricsOptions
import com.usercentrics.sdk.models.common.UsercentricsLoggerLevel

class Init {
    private val settingsId: String = "MZaDnW2Ca"//"lrekP5cHl" //"ZDQes7xES", "egLMgjg9j"

    fun initCMP(appContext: Context) {
        val userOptions = UsercentricsOptions(
            settingsId = settingsId,
            defaultLanguage = "en",
            version = "latest",
            loggerLevel = UsercentricsLoggerLevel.DEBUG
        )

        Usercentrics.initialize(appContext, userOptions)
    }

}