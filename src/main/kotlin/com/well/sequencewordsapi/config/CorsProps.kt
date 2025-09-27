package com.well.sequencewordsapi.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "security.cors")
class CorsProps {
    lateinit var allowedOrigin: String
}