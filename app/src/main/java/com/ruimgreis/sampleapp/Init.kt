package com.ruimgreis.sampleapp

import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import com.usercentrics.sdk.Usercentrics
import com.usercentrics.sdk.UsercentricsOptions
import com.usercentrics.sdk.models.common.UsercentricsLoggerLevel

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

        checkConnection(appContext)
        Usercentrics.initialize(appContext, userOptions)
    }

    fun checkConnection(appContext: Context): Boolean{
        val DEBUG_TAG = "NETWORK STATUS - "
        val connMgr = appContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        var isWifiConn: Boolean = false
        var isMobileConn: Boolean = false
        connMgr.allNetworks.forEach { network ->
            connMgr.getNetworkInfo(network)!!.apply {
                if (type == ConnectivityManager.TYPE_WIFI) {
                    isWifiConn = isWifiConn or isConnected
                }
                if (type == ConnectivityManager.TYPE_MOBILE) {
                    isMobileConn = isMobileConn or isConnected
                }
            }
        }
        Log.d(DEBUG_TAG, "Wifi connected: $isWifiConn")
        Log.d(DEBUG_TAG, "Mobile connected: $isMobileConn")

        return isWifiConn
    }

