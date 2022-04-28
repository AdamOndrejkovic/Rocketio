package com.ao.rocketio.data

data class Data(
    val description: String,
    val events: List<Event>,
    val link: String,
    val title: String
)
