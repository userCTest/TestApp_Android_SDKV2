package com.ruimgreis.sampleapp

import android.app.Application
import android.content.Context
import com.usercentrics.sdk.Usercentrics
import com.usercentrics.sdk.Usercentrics.reset
import com.usercentrics.sdk.UsercentricsOptions
import com.usercentrics.sdk.models.common.UsercentricsLoggerLevel

class Init {
    //This class can be used (with a huge refactoring) to take into account multiple settingsIds

    //private var settingsId: String = "ZDQes7xES" //"ZDQes7xES", "egLMgjg9j"

    fun initCMP(appContext: Context, settingsId: String = "ZDQes7xES") {
        val userOptions = UsercentricsOptions(
            settingsId = settingsId,
            defaultLanguage = "en",
            version = "latest",
            loggerLevel = UsercentricsLoggerLevel.DEBUG
        )

        Usercentrics.initialize(appContext, userOptions)
    }

}