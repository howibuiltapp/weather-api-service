package com.ckai.weather.service

import com.ckai.weather.service.openweathermap.model.UnitsFormat
import com.ckai.weather.service.openweathermap.model.WeatherResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController("/weather")
class WeatherRestController @Autowired constructor(val weatherService: WeatherService) {

    // TODO: Exception Handler

    @GetMapping(produces = [MediaType.APPLICATION_JSON_UTF8_VALUE])
    fun weatherByName(
        @RequestParam("q") name: String,
        @RequestParam("format", required = false) format: String?
    ): Mono<WeatherResponse> {
        return weatherService.weatherByName(
            name,
            when (format?.toLowerCase()) {
                "metric" -> UnitsFormat.METRIC
                "imperial" -> UnitsFormat.IMPERIAL
                else -> UnitsFormat.STANDARD
            }
        )
    }
}