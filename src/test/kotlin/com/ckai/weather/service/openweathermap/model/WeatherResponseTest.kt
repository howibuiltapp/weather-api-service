package com.ckai.weather.service.openweathermap.model

import com.fasterxml.jackson.databind.ObjectMapper
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers
import reactor.test.StepVerifier

@RunWith(SpringRunner::class)
@SpringBootTest
class WeatherResponseTest {

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @Test
    fun testJsonToObject_whenCityIsLosAngeles() {

        StepVerifier
            .create(
                Mono.fromCallable {
                    val url = WeatherResponseTest::class.java
                        .classLoader
                        .getResource("openweathermap/model/weather-los-angeles.json")
                    objectMapper.readValue(url, WeatherResponse::class.java)
                }
                    .subscribeOn(Schedulers.elastic())
            )
            .assertNext { response ->
                assertThat(response).isNotNull
                assertThat(response.code).isEqualTo(200)
                assertThat(response.city).isEqualTo("Los Angeles")
                assertThat(response.coordinate)
                    .isEqualTo(WeatherResponse.Coordinate(34.05, -118.24))
                assertThat(response.weather)
                    .isEqualTo(
                        listOf(
                            WeatherResponse.Weather(500, "Rain", "light rain", "10d"),
                            WeatherResponse.Weather(701, "Mist", "mist", "50d")
                        )
                    )
                assertThat(response.main)
                    .isEqualTo(WeatherResponse.Main(286.96, 1020.0, 82.0, 284.82, 289.26))
                assertThat(response.rain).isEqualTo(WeatherResponse.Rain(0.3))
            }
            .verifyComplete()
    }

    @Test
    fun testJsonToObject_whenCityIsOttawaAndUnitIsCelsius() {

        StepVerifier
            .create(
                Mono.fromCallable {
                    val url = WeatherResponseTest::class.java
                        .classLoader
                        .getResource("openweathermap/model/weather-ottawa-celsius.json")
                    objectMapper.readValue(url, WeatherResponse::class.java)
                }
                    .subscribeOn(Schedulers.elastic())
            )
            .assertNext {
                assertThat(it).isNotNull
                assertThat(it.snow).isEqualTo(WeatherResponse.Snow(0.07))
                assertThat(it.rain).isNull()
                assertThat(it.city).isEqualTo("Ottawa")
            }
            .verifyComplete()
    }

    @Test
    fun testJsonToObject_when401() {
        StepVerifier
            .create(
                Mono.fromCallable {
                    val url = WeatherResponseTest::class.java
                        .classLoader
                        .getResource("openweathermap/model/weather-401.json")
                    objectMapper.readValue(url, ErrorResponse::class.java)
                }
                    .subscribeOn(Schedulers.elastic())
            )
            .assertNext {
                assertThat(it).isNotNull
                assertThat(it.code).isEqualTo(401)
                assertThat(it.message).isEqualTo("Invalid API key. Please see http://openweathermap.org/faq#error401 for more info.")
            }
            .verifyComplete()
    }

    @Test
    fun testJsonToObject_when500() {
        StepVerifier.create(
            Mono.fromCallable {
                val url = WeatherResponseTest::class.java
                    .classLoader
                    .getResource("openweathermap/model/weather-404.json")
                objectMapper.readValue(url, ErrorResponse::class.java)
            }
        ).assertNext {
            assertThat(it).isNotNull
            assertThat(it.code).isEqualTo(404)
            assertThat(it.message).isEqualTo("city not found")
        }.verifyComplete()
    }
}
