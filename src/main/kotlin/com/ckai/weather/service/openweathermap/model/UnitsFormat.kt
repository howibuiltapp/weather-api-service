package com.ckai.weather.service.openweathermap.model

enum class OpenWeatherMapUnitsFormat(val units: String) {
    /**
     * 1. Temperature: Kelvin
     * 2. Distance: Meter
     */
    STANDARD(""),

    /**
     * 1. Temperature: Celsius
     * 2. Distance: Meter
     */
    METRIC("metric"),

    /**
     * 1. Temperature: Fahrenheit
     * 2. Distance: Mile
     */
    IMPERIAL("imperial")

}
