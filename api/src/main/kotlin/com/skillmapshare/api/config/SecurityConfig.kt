package com.skillmapshare.api.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.skillmapshare.api.auth.FirebaseJwtAuthFilter
import com.skillmapshare.api.auth.model.SecurityProperties
import com.skillmapshare.api.auth.handler.UnAuthorizeResponseHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import java.lang.Exception
import kotlin.jvm.Throws
import org.springframework.security.config.http.SessionCreationPolicy

import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
  securedEnabled = true,
  jsr250Enabled = true,
  prePostEnabled = true
)
class SecurityConfig : WebSecurityConfigurerAdapter() {
  @Autowired
  private lateinit var objectMapper : ObjectMapper
  @Autowired
  private lateinit var firebaseJwtAuthFilter : FirebaseJwtAuthFilter
  @Autowired
  private lateinit var customSecurityProps : SecurityProperties

  @Bean
    /**
     * 認証失敗時のレスポンス
     */
  fun restAuthenticationEntryPoint(): AuthenticationEntryPoint {
    return UnAuthorizeResponseHandler(objectMapper)
  }

  @Bean
  fun corsConfigurationSource() : CorsConfigurationSource {
    val config  = CorsConfiguration()
    config.allowedOrigins = customSecurityProps.allowedOrigins
    config.allowedMethods = customSecurityProps.allowedMethods
    config.allowedHeaders = customSecurityProps.allowedHeaders
    val source = UrlBasedCorsConfigurationSource()
    source.registerCorsConfiguration("/**", config)
    return source
  }

  @Throws(Exception::class)
  override fun configure(http : HttpSecurity?) {
    http?.cors()?.configurationSource(corsConfigurationSource())?.and()?.csrf()?.disable()?.formLogin()?.disable()
      ?.httpBasic()?.disable()?.exceptionHandling()?.authenticationEntryPoint(restAuthenticationEntryPoint())?.and()
      ?.authorizeRequests()?.antMatchers(*customSecurityProps.allowedPublicApis.toTypedArray())?.permitAll()
      ?.antMatchers(HttpMethod.OPTIONS, "/**")?.permitAll()?.anyRequest()?.authenticated()?.and()
      ?.addFilterBefore(firebaseJwtAuthFilter, UsernamePasswordAuthenticationFilter::class.java)?.sessionManagement()
      ?.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
  }
}
