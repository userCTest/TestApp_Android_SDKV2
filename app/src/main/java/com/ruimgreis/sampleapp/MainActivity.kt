package com.ruimgreis.sampleapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.contentValuesOf
import com.usercentrics.sdk.Usercentrics
import com.usercentrics.sdk.Usercentrics.reset
import com.usercentrics.sdk.UsercentricsPredefinedUI
import com.usercentrics.sdk.UsercentricsServiceConsent
import kotlinx.android.synthetic.main.activity_main.*
import com.ruimgreis.sampleapp.Init
import com.usercentrics.sdk.UsercentricsOptions
import com.usercentrics.sdk.models.common.UsercentricsLoggerLevel

class MainActivity : AppCompatActivity() {

    private val usercentricsView by lazy { findViewById<UsercentricsPredefinedUI>(R.id.usercentrics_view) }
    private var settingsId: String = "MZaDnW2Ca"

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
            val initUC = Init().initCMP(applicationContext)
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

        var controllerID:String? = null
        controllerID = "3022d5669c446e077e046e8de564e0401f2e21687e49da1d982aabf56a6c32fd"

        println("ControllerID: ${controllerID}.")

        if(controllerID.isNullOrEmpty()) {

            Usercentrics.isReady(
                { status ->
                    println("SHOULD SHOW CMP: ${status.shouldShowCMP}.")
                    getCMPData(null)
                    usercentricsView.load { userResponse ->
                        println("Applying the consents.")
                        //applyConsents(userResponse?.consents)
                        getCMPData(status.consents)
                        // Dismiss the CMP - finish your Activity/Fragment, remove the view, and so on
                        finish()

                    }
                },
                { error ->
                    println("Error on initialization: $error.message")
                }
            )
        } else {
            Usercentrics.instance.restoreUserSession(controllerID!!, { status ->
                // This callback is equivalent to `isReady`
                println("SHOULD SHOW CMP FROM CONTROLLERID: ${status.shouldShowCMP}.")
                getCMPData(null)
                usercentricsView.load { userResponse ->
                    println("Applying the consents.")
                    //applyConsents(userResponse?.consents)
                    getCMPData(status.consents)
                    // Dismiss the CMP - finish your Activity/Fragment, remove the view, and so on

                    finish()

                }
            }, { error ->
                println("Error on initialization with controllerID: $error.message")
            })

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
                println("EXPLICIT CONSENTS: ${consents}");
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