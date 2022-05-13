package com.ao.rocketio.data

/* Event for map */
data class Event(
    val categories: List<Category>,
    val description: String,
    val geometries: List<Geometry>,
    val id: String,
    val link: String,
    val sources: List<Source>,
    val title: String
)
