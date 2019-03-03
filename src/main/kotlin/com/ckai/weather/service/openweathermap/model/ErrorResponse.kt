package com.ckai.weather.service.openweathermap.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class ErrorResponse(
        @JsonProperty("cod") val code: Int,
        @JsonProperty("message") val message: String? = null
)