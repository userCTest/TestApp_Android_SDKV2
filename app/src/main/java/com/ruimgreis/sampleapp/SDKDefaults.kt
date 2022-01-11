package com.ruimgreis.sampleapp

class SDKDefaults {

    /**
     * Default values for properties in SDK
     * example settingsId = "bsKaFY7cM", //"ZDQes7xES", "egLMgjg9j"
     */

    var controllerID: String? = "1727720271ab52d129c92f7b9ee5bf792c6d7b90489d9fa6d36de0ffbb83dc1c"
    var isFirstLayer: Boolean = false
    var settingsId: String = "egLMgjg9j"


    fun checkControllerId(): Boolean {
        if (controllerID.isNullOrEmpty()) {
            return true
            println("ControllerID: ${controllerID}.")
        }
        return false
    }

}