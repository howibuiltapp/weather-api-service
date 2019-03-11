package com.ckai.weather.service.openweathermap

import com.ckai.weather.service.openweathermap.exception.OpenWeatherMapClientException
import com.ckai.weather.service.openweathermap.exception.OpenWeatherMapServerException
import com.ckai.weather.service.openweathermap.model.UnitsFormat
import com.ckai.weather.service.openweathermap.model.WeatherResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers

@Component
class OpenWeatherMapApiClient @Autowired constructor(openWeatherMapWebClient: WebClient) {

    val webClient = openWeatherMapWebClient

    companion object {
        const val URI_WEATHER = "/data/2.5/weather"
    }

    fun weatherByName(
        name: String,
        unitsFormat: UnitsFormat = UnitsFormat.STANDARD
    ): Mono<WeatherResponse> {
        return webClient.get()
            .uri { builder ->
                builder.path(URI_WEATHER).queryParam("q", name)
                if (unitsFormat != UnitsFormat.STANDARD) {
                    builder.queryParam("format", unitsFormat.format)
                }
                builder.build()
            }
            .retrieve()
            .onStatus({ it.is4xxClientError }, { Mono.error(OpenWeatherMapClientException(it)) })
            .onStatus({ it.is5xxServerError }, { Mono.error(OpenWeatherMapServerException(it)) })
            .bodyToMono(WeatherResponse::class.java)
            .subscribeOn(Schedulers.elastic())
    }
}

