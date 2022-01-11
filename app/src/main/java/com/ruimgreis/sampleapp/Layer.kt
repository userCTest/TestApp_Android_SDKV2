package com.ruimgreis.sampleapp

import android.content.Context
import com.usercentrics.sdk.BannerSettings
import com.usercentrics.sdk.FirstLayerStyleSettings
import com.usercentrics.sdk.UsercentricsBanner
import com.usercentrics.sdk.UsercentricsLayout

/**
    Banner API V2
    defines settings for this setup
 */
class Layer {


    private val utils = Utils()

    fun showFirstLayer(context: Context){
        // Applies to First and Second Layer, and overwrites Admin Interface Styling Settings
        val bannerSettings = BannerSettings(
            //font = <UsercentricsFont?>,
            //logo = <UsercentricsImage?>
        )

        // Applies to First Layer, and overwrites General Settings
        val firstLayerSettings = FirstLayerStyleSettings(
            //logo = <LogoSettings?>,
            //title = <TitleSettings?>,
            //message = <MessageSettings?>,
            //buttons = <List<ButtonSettings>?>,
            //backgroundColor = <@ColorInt Int?>,
            cornerRadius = 5
            //overlayColor = <@ColorInt Int?>,
        )

        // Create a UsercentricsUserInterface instance
        val ui = UsercentricsBanner(context, bannerSettings)

        // Show First Layer and handle result
        ui.showFirstLayer(layout = UsercentricsLayout.Sheet, settings = firstLayerSettings) {
                userResponse ->
            // Apply Consent
            utils.applyConsents(userResponse?.consents)}
    }

}