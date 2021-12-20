package com.ruimgreis.sampleapp

import android.app.Application
import android.content.Context
import com.usercentrics.sdk.Usercentrics
import com.usercentrics.sdk.UsercentricsOptions
import com.usercentrics.sdk.models.common.UsercentricsLoggerLevel

class Init {
    //This class can be used (with a huge refactoring) to take into account multiple settingsIds
    private val settingsId: String = "q10LWXAJr"//"lrekP5cHl" //"ZDQes7xES", "egLMgjg9j"

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