package com.ruimgreis.sampleapp

import android.app.Application
import android.content.Context
import com.usercentrics.sdk.Usercentrics
import com.usercentrics.sdk.Usercentrics.reset
import com.usercentrics.sdk.UsercentricsOptions
import com.usercentrics.sdk.models.common.UsercentricsLoggerLevel

class Init {
    /**
     * SDK initialization
     *
     * This class can be used (with a huge refactoring) to take into account multiple settingsIds
     */

    fun initCMP(appContext: Context, settingsId: String = SDKDefaults().settingsId) {
        val userOptions = UsercentricsOptions(
            settingsId = settingsId,
            defaultLanguage = "en",
            version = "latest",
            loggerLevel = UsercentricsLoggerLevel.DEBUG
        )

        Usercentrics.initialize(appContext, userOptions)
    }

}