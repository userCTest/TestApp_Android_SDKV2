package com.ruimgreis.sampleapp

import android.content.Context
import com.usercentrics.sdk.*

/**
        Banner API V2
        defines settings for this setup
     */


    fun showFirstLayer(context: Context, popup: UsercentricsLayout){
        // Applies to First and Second Layer, and overwrites Admin Interface Styling Settings
        val bannerSettings = BannerSettings(
            //font = <UsercentricsFont?>,
            //logo = <UsercentricsImage?>
        )

        //val bannerImage = HeaderImageSettings.ExtendedLogoSettings(UsercentricsImage.ImageDrawableId(R.drawable.banner))
        val acceptButton = ButtonSettings(ButtonType.ACCEPT_ALL)
        val denyButton = ButtonSettings(ButtonType.DENY_ALL)
        val moreButton = ButtonSettings(ButtonType.MORE)
        val buttons = listOf<ButtonSettings>(acceptButton, denyButton, moreButton)

        // Applies to First Layer, and overwrites General Settings
        val firstLayerSettings = FirstLayerStyleSettings(
            //headerImage = bannerImage,
            title = null,
            message = null,
            backgroundColor = null,
            //buttonLayout = ButtonLayout.Column(buttons),
            overlayColor = null,
            cornerRadius = null,
        )

        // Create a UsercentricsUserInterface instance
        val ui = UsercentricsBanner(context, bannerSettings)

        // Show First Layer and handle result
        ui.showFirstLayer(layout = popup, settings = firstLayerSettings) {
                userResponse ->
            // Apply Consent
            applyConsents(userResponse?.consents)}
    }

