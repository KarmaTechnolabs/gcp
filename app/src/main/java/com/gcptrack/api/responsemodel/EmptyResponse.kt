package com.gcptrack.api.responsemodel

import androidx.annotation.Keep

@Keep
data class EmptyResponse(
    val message: String,
    val status: Int
)