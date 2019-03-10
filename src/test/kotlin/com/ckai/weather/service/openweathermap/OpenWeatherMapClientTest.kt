package com.ckai.weather.service.openweathermap

import com.ckai.weather.service.openweathermap.exception.OpenWeatherMapClientException
import com.ckai.weather.service.openweathermap.exception.OpenWeatherMapServerException
import com.ckai.weather.service.openweathermap.model.OpenWeatherMapUnitsFormat
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.web.reactive.function.client.WebClient
import reactor.test.StepVerifier
import java.io.File

@RunWith(SpringRunner::class)
@SpringBootTest
class OpenWeatherMapApiClientTest {

    val server: MockWebServer = MockWebServer()
        @Rule get

    @Autowired
    private lateinit var openWeatherMapWebClient: WebClient

    private lateinit var apiClient: OpenWeatherMapApiClient

    @Before
    fun before() {

        val webClient = openWeatherMapWebClient.mutate()
            .baseUrl(server.url("/").toString())
            .build()
        apiClient = OpenWeatherMapApiClient(webClient)
    }

    @Test
    fun testWeatherByName() {
        val url = javaClass.classLoader.getResource("openweathermap/model/weather-los-angeles.json")

        server.enqueue(
            MockResponse()
                .setResponseCode(200)
                .addHeader("Content-Type", "application/json; charset=utf-8")
                .addHeader("X-Cache-Key", "/data/2.5/weather?q=los angeles")
                .setBody(File(url.file).readText())
        )

        StepVerifier
            .create(apiClient.weatherByName("Los Angeles"))
            .assertNext { assertThat(it.city).isEqualTo("Los Angeles") }
            .verifyComplete()

        val request = server.takeRequest()
        assertThat(request.requestUrl.queryParameterNames()).isEqualTo(setOf("q", "appid"))
    }

    @Test
    fun testWeatherByName_unitsMetric() {

        val url = javaClass.classLoader.getResource("openweathermap/model/weather-los-angeles.json")

        server.enqueue(
            MockResponse()
                .setResponseCode(200)
                .addHeader("Content-Type", "application/json; charset=utf-8")
                .addHeader("X-Cache-Key", "/data/2.5/weather?q=los angeles")
                .setBody(File(url.file).readText())
        )

        StepVerifier
            .create(apiClient.weatherByName("Los Angeles", OpenWeatherMapUnitsFormat.METRIC))
            .assertNext { assertThat(it.city).isEqualTo("Los Angeles") }
            .verifyComplete()

        val request = server.takeRequest()
        assertThat(request.requestUrl.queryParameterNames()).isEqualTo(setOf("q", "appid", "units"))
        assertThat(request.requestUrl.queryParameterValues("units")).isEqualTo(listOf("metric"))
    }

    @Test
    fun testWeatherByName_whenReceive401() {
        val url = javaClass.classLoader.getResource("openweathermap/model/weather-401.json")
        server.enqueue(
            MockResponse().setResponseCode(401)
                .setBody(File(url.file).readText())
        )

        StepVerifier
            .create(apiClient.weatherByName("Los Angeles"))
            .verifyError(OpenWeatherMapClientException::class.java)
    }


    @Test
    fun testWeatherByName_whenReceive500() {
        server.enqueue(MockResponse().setResponseCode(500))
        StepVerifier
            .create(apiClient.weatherByName("Los Angeles"))
            .verifyError(OpenWeatherMapServerException::class.java)
    }
}