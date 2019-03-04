package com.ckai.weather.service.openweathermap

import com.ckai.weather.service.openweathermap.exception.OpenWeatherMapClientException
import com.ckai.weather.service.openweathermap.exception.OpenWeatherMapServerException
import com.ckai.weather.service.openweathermap.model.WeatherResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers

@Component
class OpenWeatherMapClient @Autowired constructor(openWeatherMapApiClient: WebClient) {

    val apiClient = openWeatherMapApiClient

    companion object {
        const val URI_WEATHER = "/data/2.5/weather"
    }

    fun weatherByName(name: String): Mono<WeatherResponse> {
        return apiClient.get()
                .uri { builder -> builder.path(URI_WEATHER).queryParam("q", name).build() }
                .retrieve()
                .onStatus({ it.is4xxClientError }, { Mono.error(OpenWeatherMapClientException(it)) })
                .onStatus({ it.is5xxServerError }, { Mono.error(OpenWeatherMapServerException(it)) })
                .bodyToMono(WeatherResponse::class.java)
                .subscribeOn(Schedulers.elastic())
    }
}

