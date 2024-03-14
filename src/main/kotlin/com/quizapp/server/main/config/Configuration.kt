package com.quizapp.server.main.config

import com.quizapp.server.main.repository.user.UserRepository
import com.quizapp.server.main.service.CustomUserDetailsService
import com.quizapp.server.main.config.JwtProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration
@EnableConfigurationProperties(JwtProperties::class)
class Configuration {


    @Bean
    fun userDetailsService(userRepository: UserRepository) = CustomUserDetailsService(userRepository)

    @Bean
    fun encoder(): PasswordEncoder = BCryptPasswordEncoder()

    @Bean
    fun authenticationProvider(userRepository: UserRepository): AuthenticationProvider =
            DaoAuthenticationProvider()
                    .also {
                        it.setUserDetailsService(userDetailsService(userRepository))
                        it.setPasswordEncoder(encoder())
                    }

    @Bean
    fun authenticationManager(config : AuthenticationConfiguration): AuthenticationManager =
            config.authenticationManager
}