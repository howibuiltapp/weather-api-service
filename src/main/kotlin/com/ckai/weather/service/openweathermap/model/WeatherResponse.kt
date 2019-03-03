package com.ckai.weather.service.openweathermap.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class WeatherResponse(
        @JsonProperty("coord") val coordinate: Coordinate?,
        @JsonProperty("weather") val weather: List<Weather>?,
        @JsonProperty("base") val base: String?,
        @JsonProperty("main") val main: Main?,
        @JsonProperty("wind") val wind: Wind?,
        @JsonProperty("clouds") val clouds: Clouds?,
        @JsonProperty("rain") val rain: Rain?,
        @JsonProperty("snow") val snow: Snow?,
        @JsonProperty("dt") val dt: Long?,
        @JsonProperty("id") val cityId: Long?,
        @JsonProperty("name") val city: String?,
        @JsonProperty("visibility") val visibility: Long?,
        @JsonProperty("sys") val sys: HashMap<String, Any>?
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
            @JsonProperty("temp") val temperature: Double,
            @JsonProperty("pressure") val pressure: Double,
            @JsonProperty("humidity") val humidity: Double,
            @JsonProperty("temp_min") val minimum: Double,
            @JsonProperty("temp_max") val maximum: Double,
            @JsonProperty("sea_level") val seaLevel: Double,
            @JsonProperty("grnd_level") val groundLevel: Double
    )

    data class Wind(
            @JsonProperty("speed") val speed: Double,
            @JsonProperty("deg") val degree: Double
    )

    data class Clouds(
            @JsonProperty("all") val cloudiness: Double
    )

    data class Rain(
            @JsonProperty("1h") val oneHour: Double,
            @JsonProperty("3h") val threeHours: Double
    )

    data class Snow(
            @JsonProperty("1h") val oneHour: Double,
            @JsonProperty("3h") val threeHours: Double
    )

}