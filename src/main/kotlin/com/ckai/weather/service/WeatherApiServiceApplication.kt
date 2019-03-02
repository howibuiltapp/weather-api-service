package com.ckai.weather.service

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class WeatherApiServiceApplication

fun main(args: Array<String>) {
    runApplication<WeatherApiServiceApplication>(*args)
}
