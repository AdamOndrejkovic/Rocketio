package com.ao.rocketio.data

/* Data class for Mars Weather feature */
data class Weather(
    val TZ_Data: String,
    val abs_humidity: Any,
    val atmo_opacity: String,
    val id: Int,
    val local_uv_irradiance_index: String,
    val ls: Int,
    val max_gts_temp: Int,
    val max_temp: Int,
    val min_gts_temp: Int,
    val min_temp: Int,
    val pressure: Int,
    val pressure_string: String,
    val season: String,
    val sol: Int,
    val status: Int,
    val sunrise: String,
    val sunset: String,
    val terrestrial_date: String,
    val unitOfMeasure: String,
    val wind_direction: Any,
    val wind_speed: Any
)