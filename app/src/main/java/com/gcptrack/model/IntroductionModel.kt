package com.gcptrack.model

import androidx.annotation.DrawableRes

data class IntroductionModel(
    @DrawableRes val drawableResourceId: Int,
    val title: String,
    val detail: String
)