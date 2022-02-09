package com.ruimgreis.sampleapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.ruimgreis.sampleapp.databinding.ActivityMainBinding
import com.usercentrics.sdk.Usercentrics.reset
import com.usercentrics.sdk.UsercentricsActivityContract
import com.usercentrics.sdk.UsercentricsUISettings

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val launchView = LaunchView()
    private val defaults = SDKDefaults()

    private val usercentricsActivityLauncher =
        registerForActivityResult(UsercentricsActivityContract()) { services ->
            Log.d(MainActivity::class.simpleName, "registerForActivityResult services: $services")
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // version input
        binding.txtVersion.text = getVersionName(this)

        // go button input to get settingsId
        binding.btnGo.setOnClickListener {
            showCMP()
        }

        // btn close app
        binding.btnResetCmp.setOnClickListener {
            reset()
            initCMP(applicationContext)
        }
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
    private fun showCMP() {
        checkConnection(this)

        when {
            defaults.isFirstLayer -> {
                showFirstLayer(this)
            }
            defaults.checkControllerId() -> {
                launchView.launchWithoutControllerID(binding.usercentricsView, this)
            }
            else -> {
                launchView.launchWithControllerID(defaults.controllerID, binding.usercentricsView, this)
            }
        }
    }
}