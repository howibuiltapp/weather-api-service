package com.ckai.weather.service.openweathermap.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import org.springframework.web.reactive.function.client.ClientRequest
import org.springframework.web.reactive.function.client.ExchangeFilterFunction
import org.springframework.web.reactive.function.client.ExchangeStrategies
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.util.UriComponentsBuilder
import reactor.core.publisher.Mono

@Configuration
@PropertySource("classpath:openweathermap.properties")
class OpenWeatherMapConfig {

    @Bean
    fun openWeatherMapApiClient(
            @Value("\${openweathermap.baseUrl}") baseUrl: String,
            @Value("\${openweathermap.apiKey}") apiKey: String): WebClient {
        return WebClient.builder()
                .baseUrl(baseUrl)
                .exchangeStrategies(ExchangeStrategies.builder()
                        .codecs { it.defaultCodecs().enableLoggingRequestDetails(true) }
                        .build())
                .filter(ExchangeFilterFunction.ofRequestProcessor {
                    val url = UriComponentsBuilder.fromUri(it.url())
                            .queryParam("appid", apiKey)
                            .build()
                            .toUri()
                    val request = ClientRequest.from(it).url(url)
                            .build()
                    Mono.just(request)
                })
                .build()
    }

}
