package com.ruimgreis.sampleapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.usercentrics.sdk.Usercentrics.reset
import kotlinx.android.synthetic.main.activity_main.*
import com.ruimgreis.sampleapp.*
import com.usercentrics.sdk.*
import com.usercentrics.sdk.models.common.UsercentricsLoggerLevel

class MainActivity : AppCompatActivity() {

    private val usercentricsView by lazy { findViewById<UsercentricsPredefinedUI>(R.id.usercentrics_view) }
    private var controllerID: String? = null
    private val initUC: Init = Init()

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
            initUC.initCMP(applicationContext)
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

    /*
     * func used to show the CMP, either with a settingsID or a controllerId (needs refactoring),
     * maybe using an ID object that contains the id: String and a type: enum {SETTINGS_ID, CONTROLLER_ID}
     */
    private fun showCMP() {

        controllerID = "a9d8ef2ffc16bc7c5ae8111dd0d442190371c8f4cf028b1d3218dd92cf7e0fab"

        println("ControllerID: ${controllerID}.")

        if(controllerID.isNullOrEmpty()) {
            launchWithoutControllerID(usercentricsView)
        } else {
            launchWithControllerID(controllerID, usercentricsView)
        }
    }

    private fun launchWithoutControllerID(usercentricsView: UsercentricsPredefinedUI) {
        Usercentrics.isReady(
            { status ->
                showView(status, usercentricsView)
            },
            { error ->
                println("Error on initialization: $error.message")
            }
        )
    }

    private fun launchWithControllerID(controllerID: String?, usercentricsView: UsercentricsPredefinedUI) {
        reset()
        initUC.initCMP(applicationContext, "egLMgjg9j")
        Usercentrics.instance.restoreUserSession(controllerID!!, { status ->
            showView(status, usercentricsView)
        }, { error ->
            println("Error on initialization with controllerID: $error.message")
        })
    }

    private fun showView(
        status: UsercentricsReadyStatus,
        usercentricsView: UsercentricsPredefinedUI
    ) {
        println("SHOULD SHOW CMP: ${status.shouldShowCMP}.")
        getCMPData(null)
        usercentricsView.load {
            println("Applying the consents.")
            //applyConsents(userResponse?.consents)
            getCMPData(status.consents)
            // Dismiss the CMP - finish your Activity/Fragment, remove the view, and so on
            finish()
        }
    }

    private fun applyConsents(consents: List<UsercentricsServiceConsent>?) {
        if (consents != null) {
            if (consents.isNotEmpty()) {
                println("-- DPS -- ")
                for (service in consents) {
                    println("EXPLICIT CONSENTS: \n DPS: ${service.dataProcessor} - TemplateId: ${service.templateId} - Consent Value: ${service.status}")
                }
            }
        }
    }

    private fun getCMPData(consents: List<UsercentricsServiceConsent>?) {
        Usercentrics.isReady({

            // CMP Data
            val data = Usercentrics.instance.getCMPData()
            //val settings = data.settings
            //val tcfSettings = settings.tcf2

            // TCF Data
            val tcfData = Usercentrics.instance.getTCFData()
            val purposes = tcfData.purposes
            //val specialPurposes = tcfData.specialPurposes
            //val features = tcfData.features
            //val specialFeatures = tcfData.specialFeatures
            //val stacks = tcfData.stacks
            val vendors = tcfData.vendors

            // Non-TCF Data - if you have services not included in IAB
            val services = data.services
            val categories = data.categories
            print("IMPLICIT CONSENTS: \n")
            for (service in services) {
                println("DPS: ${service.dataProcessor} -- Default Consent Status: ${service.defaultConsentStatus}")
            }

            if(consents.isNullOrEmpty()) {
                println("CONSENTS: no explicit consents given by user yet!");
            } else {
                println("EXPLICIT CONSENTS: $consents");
            }

            // TCString"
            println("-- TCSTRING --")
            val tcString = Usercentrics.instance.getTCString()
            println("TCString: $tcString")

            println("-- PURPOSES --")
            val purposesList = purposes.sortedBy { tcfPurpose -> tcfPurpose.id }
            for (purpose in purposesList)
                println("""${purpose.name} - Id: ${purpose.id} - Consent: ${purpose.consent} - Legitimate Interest: ${purpose.legitimateInterestConsent}""")

            println("-- VENDORS WITH CONSENT TRUE--")
            var vendorsList = vendors.filter { tcfVendor -> tcfVendor.consent == true }
            vendorsList = vendorsList.sortedBy { tcfVendor -> tcfVendor.id }

            /*for(vendor in vendors) {
                if(vendor.consent == true)
                    vendorsList.add(vendor)
            }*/

            for (vendor in vendorsList) {
                println("${vendor.name} - Id: ${vendor.id}")
            }


        }, { error ->
            println("Error on initialization: $error.message")
        })
    }
}