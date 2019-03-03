package com.ckai.weather.service.openweathermap

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import reactor.test.StepVerifier

@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OpenWeatherMapClientTest {

    @Autowired
    private lateinit var client: OpenWeatherMapClient

    @Test
    fun testBasicSetup() {
        assertThat(client.apiClient).isNotNull
    }

    @Test
    fun testWeatherByName() {
        StepVerifier.create(client.weatherByName("London"))
                .assertNext { assertThat(it.city).isEqualTo("London") }
                .verifyComplete()

    }
}