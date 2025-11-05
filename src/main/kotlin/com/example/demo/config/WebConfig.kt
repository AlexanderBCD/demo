package com.example.demo.config

import org.springframework.context.annotation.Configuration
import org.springframework.format.FormatterRegistry
import org.springframework.format.datetime.DateFormatter
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig : WebMvcConfigurer {
    
    override fun addFormatters(registry: FormatterRegistry) {
        // Registrar formateador para Date que soporte el formato HTML5 datetime-local
        registry.addFormatter(DateFormatter("yyyy-MM-dd'T'HH:mm"))
    }
}
