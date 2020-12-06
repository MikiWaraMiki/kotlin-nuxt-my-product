package com.skillmapshare.api.auth.model

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties("security")
class SecurityProperties {
  lateinit var allowedOrigins : List<String>
  lateinit var allowedHeaders : List<String>
  lateinit var exposedHeaders : List<String>
  lateinit var allowedMethods : List<String>
  lateinit var allowedPublicApis : List<String>
}
