package com.ao.rocketio.data

/* Data class for MARS image feature */
data class Image(
    val date: String,
    val explanation: String,
    val media_type: String,
    val service_version: String,
    val title: String,
    val url: String
)
