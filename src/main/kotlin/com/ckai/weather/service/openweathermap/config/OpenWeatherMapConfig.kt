package com.ckai.weather.service.openweathermap.config

import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource

@Configuration
@PropertySource("classpath:openweathermap.properties")
class OpenWeatherMapConfig {

}
