package com.app.gcp.model

import androidx.annotation.DrawableRes

data class HomeSliderModel(
    @DrawableRes val sliderImage: Int,
    val title: String,
    val subTitle: String
)