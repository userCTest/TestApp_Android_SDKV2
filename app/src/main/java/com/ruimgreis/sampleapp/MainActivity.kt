package com.ruimgreis.sampleapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.usercentrics.sdk.Usercentrics.reset
import com.usercentrics.sdk.UsercentricsPredefinedUI
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val usercentricsView by lazy { findViewById<UsercentricsPredefinedUI>(R.id.usercentrics_view) }
    private val launchView = LaunchView()
    private val defaults = SDKDefaults()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // version input
        txt_version.text = getVersionName(this)

        // go button input to get settingsId
        btn_go.setOnClickListener {
            showCMP()
        }

        // btn close app
        btn_reset_cmp.setOnClickListener {
            reset()
            initCMP(applicationContext)
        }
    }

    override fun onBackPressed() {
        if (usercentricsView.onBackPressed()) return
        // ...
        super.onBackPressed()
    }

    /**
     * gets version of SDK being used
     */
    private fun getVersionName(activity: MainActivity): String {
        val pInfo = activity.packageManager.getPackageInfo(activity.packageName, 0)
        return pInfo.versionName ?: "test"
    }

    /**
     * func used to show the CMP, either with a settingsID or a controllerId (needs refactoring),
     * maybe using an ID object that contains the id: String and a type: enum {SETTINGS_ID, CONTROLLER_ID}
     */
    private fun showCMP() {

        when {
            defaults.isFirstLayer -> {
                showFirstLayer(this)
            }
            defaults.checkControllerId() -> {
                launchView.launchWithoutControllerID(usercentricsView, this)
            }
            else -> {
                launchView.launchWithControllerID(defaults.controllerID, usercentricsView, this)
            }
        }
    }
}