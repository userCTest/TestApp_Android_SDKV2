package com.ruimgreis.sampleapp

import com.usercentrics.sdk.Usercentrics
import com.usercentrics.sdk.UsercentricsServiceConsent


    /**
     * Apply user given consents
     * In this simple form, it simply logs default gdpr consents to console
     */
    fun applyConsents(consents: List<UsercentricsServiceConsent>?) {
        if (consents != null) {
            if (consents.isNotEmpty()) {
                println("-- DPS -- ")
                for (service in consents) {
                    println("EXPLICIT CONSENTS: \n DPS: ${service.dataProcessor} - TemplateId: ${service.templateId} - Consent Value: ${service.status}")
                }
            }
        }
    }

    /**
     * Logs settings and user given consents, GDPR and TCF
     * Comment/Uncomment whatever you want to log or not
     */

    fun getCMPData(consents: List<UsercentricsServiceConsent>?) {
        Usercentrics.isReady({

            // CMP Data
            val data = Usercentrics.instance.getCMPData()
            //val settings = data.settings
            //val tcfSettings = settings.tcf2

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

            if(Usercentrics.instance.getTCFData().vendors.isNotEmpty()) {
                // TCF Data
                val tcfData = Usercentrics.instance.getTCFData()
                val purposes = tcfData.purposes
                //val specialPurposes = tcfData.specialPurposes
                //val features = tcfData.features
                //val specialFeatures = tcfData.specialFeatures
                //val stacks = tcfData.stacks
                val vendors = tcfData.vendors

                // TCString"
                println("-- TCSTRING --")
                val tcString = Usercentrics.instance.getTCString()
                println("TCString: $tcString")

                println("-- PURPOSES --")
                val purposesList = purposes.sortedBy { tcfPurpose -> tcfPurpose.id }
                for (purpose in purposesList)
                    println("""${purpose.name} - Id: ${purpose.id} - Consent: 
                        |${purpose.consent} - Legitimate Interest: ${purpose.legitimateInterestConsent}
                        |Show LI Toggle: ${purpose.showLegitimateInterestToggle}""".trimMargin())

                println("-- NUMBER OF VENDORS: ${vendors.size}")

                println("-- VENDORS WITH CONSENT TRUE--")
                var vendorsList = vendors.filter { tcfVendor -> tcfVendor.consent == true }
                vendorsList = vendorsList.sortedBy { tcfVendor -> tcfVendor.id }

                for (vendor in vendorsList) {
                    println("${vendor.name} - Id: ${vendor.id}")
                }
            }


        }, { error ->
            println("Error on initialization: $error.message")
        })
    }
