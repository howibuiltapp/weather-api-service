package com.ckai.weather.service.openweathermap.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class WeatherResponse(
        @JsonProperty("cod") val code: Int,
        @JsonProperty("coord") val coordinate: Coordinate? = null,
        @JsonProperty("weather") val weather: List<Weather>? = null,
        @JsonProperty("base") val base: String? = null,
        @JsonProperty("main") val main: Main? = null,
        @JsonProperty("wind") val wind: Wind? = null,
        @JsonProperty("clouds") val clouds: Clouds? = null,
        @JsonProperty("rain") val rain: Rain? = null,
        @JsonProperty("snow") val snow: Snow? = null,
        @JsonProperty("dt") val dt: Long? = null,
        @JsonProperty("id") val cityId: Long? = null,
        @JsonProperty("name") val city: String? = null,
        @JsonProperty("visibility") val visibility: Long? = null,
        @JsonProperty("sys") val sys: HashMap<String, Any>? = null
) {

    data class Coordinate(
            @JsonProperty("lat") val latitude: Double,
            @JsonProperty("lon") val longitude: Double
    )

    data class Weather(
            @JsonProperty("id") val id: Long,
            @JsonProperty("main") val main: String,
            @JsonProperty("description") val description: String,
            @JsonProperty("icon") val icon: String
    )

    data class Main(
            @JsonProperty("temp") val temperature: Double? = null,
            @JsonProperty("pressure") val pressure: Double? = null,
            @JsonProperty("humidity") val humidity: Double? = null,
            @JsonProperty("temp_min") val minimum: Double? = null,
            @JsonProperty("temp_max") val maximum: Double? = null,
            @JsonProperty("sea_level") val seaLevel: Double? = null,
            @JsonProperty("grnd_level") val groundLevel: Double? = null
    )

    data class Wind(
            @JsonProperty("speed") val speed: Double,
            @JsonProperty("deg") val degree: Double
    )

    data class Clouds(
            @JsonProperty("all") val cloudiness: Double
    )

    data class Rain(
            @JsonProperty("1h") val oneHour: Double? = null,
            @JsonProperty("3h") val threeHours: Double? = null
    )

    data class Snow(
            @JsonProperty("1h") val oneHour: Double? = null,
            @JsonProperty("3h") val threeHours: Double? = null
    )

}