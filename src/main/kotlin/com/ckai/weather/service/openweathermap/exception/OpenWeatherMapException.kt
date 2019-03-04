package com.ckai.weather.service.openweathermap.exception

import org.springframework.web.reactive.function.client.ClientResponse

abstract class OpenWeatherMapException(val response: ClientResponse) : Exception()

class OpenWeatherMapClientException(response: ClientResponse) : OpenWeatherMapException(response)

class OpenWeatherMapServerException(response: ClientResponse) : OpenWeatherMapException(response)



