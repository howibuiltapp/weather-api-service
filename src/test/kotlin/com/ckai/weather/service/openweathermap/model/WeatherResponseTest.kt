package com.ckai.weather.service.openweathermap.model

import com.fasterxml.jackson.databind.ObjectMapper
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest
class WeatherResponseTest {

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @Test
    fun testJsonToObject() {

        val url = WeatherResponseTest::class.java
            .classLoader.getResource("openweathermap/model/weather-london.json")
        val obj = objectMapper.readValue<WeatherResponse>(url, WeatherResponse::class.java)
        assertThat(obj).isNotNull
    }
}
