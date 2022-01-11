package com.ruimgreis.sampleapp

import com.usercentrics.sdk.Usercentrics
import com.usercentrics.sdk.UsercentricsPredefinedUI
import com.usercentrics.sdk.UsercentricsReadyStatus
import android.content.Context
import com.usercentrics.sdk.*

/**
 * SDK launching options, with or without controllerID
 */

class LaunchView {

    private val utils = Utils()
    private val defaults = SDKDefaults()

    public fun launchWithoutControllerID(usercentricsView: UsercentricsPredefinedUI, appContext: Context) {
        Usercentrics.isReady(
            { status ->
                showView(status, usercentricsView, appContext)
            },
            { error ->
                println("Error on initialization: $error.message")
            }
        )
    }

    public fun launchWithControllerID(controllerID: String?,
                                       usercentricsView: UsercentricsPredefinedUI,
                                       initUC: Init, appContext: Context) {
        Usercentrics.reset()
        initUC.initCMP(appContext, defaults.settingsId)
        Usercentrics.instance.restoreUserSession(controllerID!!, { status ->
            showView(status, usercentricsView, appContext)
        }, { error ->
            println("Error on initialization with controllerID: $error.message")
        })
    }

    /**
     * Shows predefinedUI and logs settings plus user given consents
     */
    private fun showView(
        status: UsercentricsReadyStatus,
        usercentricsView: UsercentricsPredefinedUI,
        appContext: Context
    ) {

        println("SHOULD SHOW CMP: ${status.shouldShowCMP}.")
        utils.getCMPData(null)
        usercentricsView.load {
            println("Applying the consents.")
            //applyConsents(userResponse?.consents)
            utils.getCMPData(status.consents)
        }
    }
}