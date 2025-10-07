package com.well.sequencewordsapi.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "auth")
class AuthProps {
    var jwt: Jwt = Jwt()

    class Jwt {
        lateinit var issuer: String;
        lateinit var secret: String;
        var ttlSeconds: Long = 60 * 60 * 24 * 7
    }
}
