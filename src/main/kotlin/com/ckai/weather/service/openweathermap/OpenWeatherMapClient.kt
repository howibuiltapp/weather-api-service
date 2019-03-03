package com.ckai.weather.service.openweathermap

import com.ckai.weather.service.openweathermap.model.WeatherResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Component
class OpenWeatherMapClient @Autowired constructor(@Value("\${openweathermap.apiKey}") val apiKey: String) {

    final val apiClient: WebClient

    init {
        apiClient = WebClient.builder()
                .baseUrl(BASE_URL)
                .build()
    }

    companion object {
        const val BASE_URL = "https://api.openweathermap.org"
        const val URI_WEATHER = "/data/2.5/weather"
    }

    fun weatherByName(name: String): Mono<WeatherResponse> {
        return apiClient.get()
                .uri { builder ->
                    builder.path(URI_WEATHER)
                            .queryParam("q", name)
                            .queryParam("appid", apiKey)
                            .build()
                }
                .attribute("q", name)
                .exchange()
                .flatMap { it.bodyToMono(WeatherResponse::class.java) }
    }
}



