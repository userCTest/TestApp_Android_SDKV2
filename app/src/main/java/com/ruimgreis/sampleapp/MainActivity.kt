package com.ruimgreis.sampleapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.usercentrics.sdk.Usercentrics
import com.usercentrics.sdk.Usercentrics.reset
import com.usercentrics.sdk.UsercentricsPredefinedUI
import com.usercentrics.sdk.UsercentricsServiceConsent
import com.usercentrics.sdk.services.tcf.interfaces.TCFVendor
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val usercentricsView by lazy { findViewById<UsercentricsPredefinedUI>(R.id.usercentrics_view) }

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

    private fun showCMP() {

        Usercentrics.isReady(
            { status ->
                println("Status: $status .")
                usercentricsView.load { userResponse ->
                    println("Applying the consents.")
                    applyConsents(userResponse?.consents)
                    getTCData()
                    // Dismiss the CMP - finish your Activity/Fragment, remove the view, and so on
                    finish()

                }
            },
            { error ->
                println("Error on initialization: $error.message")
            }
        )
    }

    private fun applyConsents(consents: List<UsercentricsServiceConsent>?) {
        if (consents != null) {
            if (consents.isNotEmpty()) {
                println("-- DPS -- ")
                for (service in consents) {
                    println("${service.dataProcessor} - TemplateId: ${service.templateId} - Consent Value: ${service.status}")
                }
            }
        }
    }

    private fun getTCData()
    {
        Usercentrics.isReady({

            // CMP Data
            //val data = Usercentrics.instance.getCMPData()
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
            //val services = data.services
            //val categories = data.categories

            // TCString
            println("-- TCSTRING --")
            val tcString = Usercentrics.instance.getTCString()
            println("TCString: $tcString")

            println("-- PURPOSES --")
            var purposesList = purposes.sortedBy { tcfPurpose -> tcfPurpose.id }
            for(purpose in purposesList)
                println("""${purpose.name} - Id: ${purpose.id} - Consent: ${purpose.consent} - Legitimate Interest: ${purpose.legitimateInterestConsent}""")

            println("-- VENDORS WITH CONSENT TRUE--")
            var vendorsList = vendors.filter{ tcfVendor -> tcfVendor.consent == true }
            vendorsList = vendorsList.sortedBy { tcfVendor -> tcfVendor.id }

            /*for(vendor in vendors) {
                if(vendor.consent == true)
                    vendorsList.add(vendor)
            }*/

            for(vendor in vendorsList) {
                    println("${vendor.name} - Id: ${vendor.id}")
            }


        }, { error ->
            println("Error on initialization: $error.message")
        })
    }
}