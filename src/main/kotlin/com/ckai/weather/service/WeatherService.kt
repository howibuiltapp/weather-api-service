package com.ckai.weather.service

import com.ckai.weather.service.openweathermap.OpenWeatherMapApiClient
import com.ckai.weather.service.openweathermap.model.UnitsFormat
import com.ckai.weather.service.openweathermap.model.WeatherResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class WeatherService @Autowired constructor(val apiClient: OpenWeatherMapApiClient) {

    fun weatherByName(name: String, format: UnitsFormat): Mono<WeatherResponse> {
        // TODO: Cache mechanism
        return apiClient.weatherByName(name, format)
    }

}