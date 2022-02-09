package com.ruimgreis.sampleapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.ruimgreis.sampleapp.databinding.ActivityMainBinding
import com.usercentrics.sdk.PopupPosition
import com.usercentrics.sdk.Usercentrics.reset
import com.usercentrics.sdk.UsercentricsActivityContract
import com.usercentrics.sdk.UsercentricsLayout

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val launchView = LaunchLegacyView()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /**
         * binding of the UI elements
         */
        uiBinding()
    }

    override fun onBackPressed() {
        if (binding.usercentricsView.onBackPressed()) return
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
    private fun showCMP(ucLayout: UsercentricsLayout?) {
        checkConnection(this)

        if(ucLayout.toString().length > 4) {
            showFirstLayer(this, ucLayout!!)
        } else {
                launchView.launch(binding.usercentricsView, this)
        }
    }

    /**
     * binding of the UI elements
     * Use this to bind any UI elements
     */
    private fun uiBinding() {
        // version input
        binding.txtVersion.text = getVersionName(this)

        // launch banner api v1
        binding.btnFull.setOnClickListener {
            showCMP(null)
        }

        // launch banner api v2 Full
        binding.btnFirstLayerFull.setOnClickListener {
            showCMP(UsercentricsLayout.Full)
        }

        // launch banner api v2 Sheet
        binding.btnFirstLayerSheet.setOnClickListener {
            showCMP(UsercentricsLayout.Sheet)
        }

        // launch banner api v2 Popup Bottom
        binding.btnFirstLayerBottom.setOnClickListener {
            showCMP(UsercentricsLayout.Popup(PopupPosition.BOTTOM))
        }

        // launch banner api v2 Popup Center
        binding.btnFirstLayerCenter.setOnClickListener {
            showCMP(UsercentricsLayout.Popup(PopupPosition.CENTER))
        }

        // btn close app
        binding.btnResetCmp.setOnClickListener {
            reset()
            initCMP(applicationContext)
        }
    }
}