package com.quizapp.server.main.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.quizapp.server.main.repository.user.UserType
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.InsufficientAuthenticationException
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.web.DefaultSecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.servlet.HandlerExceptionResolver

@Configuration
@EnableWebSecurity
class SecurityConfiguration(
    private val authenticationProvider: AuthenticationProvider
) {

    @Autowired
    @Qualifier("handlerExceptionResolver")
    lateinit var exceptionResolver : HandlerExceptionResolver

    @Bean
    fun jwtAuthenticationFilter() : JwtAuthenticationFilter {
        return JwtAuthenticationFilter(exceptionResolver)
    }


    @Bean
    fun securityFilterChain(
        http: HttpSecurity,
        jwtAuthenticationFilter: JwtAuthenticationFilter
    ): DefaultSecurityFilterChain =
        http.csrf { it.disable() }
            .authorizeHttpRequests {
                it
                    .requestMatchers("/api/auth", "api/auth/refresh", "/error", "/actuator")
                    .permitAll()
                    .requestMatchers(HttpMethod.POST, "/api/register")
                    .permitAll()
                    .requestMatchers("/api/admin**")
                    .hasRole(UserType.ADMIN.name)
                    .requestMatchers("api/user**")
                    .hasRole(UserType.USER.name)
                    .anyRequest()
                    .fullyAuthenticated()
            }
            .sessionManagement {
                it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
            .authenticationProvider(authenticationProvider)
            .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter::class.java)
            .exceptionHandling {
                it.authenticationEntryPoint { _, response, authException ->
                    val objectMapper = ObjectMapper()
                    response.contentType = MediaType.APPLICATION_JSON_VALUE

                    when (authException) {
                        is UsernameNotFoundException -> {
                            response.status = HttpServletResponse.SC_UNAUTHORIZED
                            val errorMessage = objectMapper.writeValueAsString(mapOf("status" to HttpServletResponse.SC_UNAUTHORIZED, "error" to "User not found"))
                            response.writer.write(errorMessage)
                        }
                        is BadCredentialsException -> {
                            response.status = HttpServletResponse.SC_UNAUTHORIZED
                            val errorMessage = objectMapper.writeValueAsString(mapOf("status" to HttpServletResponse.SC_UNAUTHORIZED, "error" to "Invalid credentials"))
                            response.writer.write(errorMessage)
                        }
                        is InsufficientAuthenticationException -> {
                            response.status = HttpServletResponse.SC_UNAUTHORIZED
                            val errorMessage = objectMapper.writeValueAsString(mapOf("status" to HttpServletResponse.SC_UNAUTHORIZED, "error" to "Not Authorized to access"))
                            response.writer.write(errorMessage)
                        }
                        else -> {
                            response.status = HttpServletResponse.SC_INTERNAL_SERVER_ERROR
                            val errorMessage = objectMapper.writeValueAsString(mapOf("status" to HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "error" to "Internal server error"))
                            response.writer.write(errorMessage)
                        }
                    }
                }
            }
            .build()
}