package com.ruimgreis.sampleapp

class SDKDefaults {

    /**
     * Default values for properties in SDK
     * example settingsId = "ZDQes7xES", "egLMgjg9j"
     */

    var controllerID: String? = null //"1727720271ab52d129c92f7b9ee5bf792c6d7b90489d9fa6d36de0ffbb83dc1c"
    var isFirstLayer: Boolean = false
    var settingsId: String = "ZDQes7xES"


    fun checkControllerId(): Boolean {
        if (controllerID.isNullOrEmpty()) {
            println("ControllerID: ${controllerID}.")
            return true
        }
        return false
    }

}