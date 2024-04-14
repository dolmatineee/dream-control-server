package com.example

import com.example.authentication.JwtService
import com.example.data.model.repository.DreamRepositoryImpl
import com.example.data.model.repository.UserRepositoryImpl
import com.example.domain.usecase.DreamUseCase
import com.example.domain.usecase.UserUseCase
import com.example.plugins.*
import com.example.plugins.DatabasesFactory.initializationDatabase
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {

    val jwtService = JwtService()
    val userRepository = UserRepositoryImpl()
    val dreamRepository = DreamRepositoryImpl()
    val userUseCase = UserUseCase(userRepository, jwtService)
    val dreamUseCase = DreamUseCase(dreamRepository)

    initializationDatabase()
    configureSecurity(userUseCase)
    configureMonitoring()
    configureSerialization()
//    configureDatabases()
//    configureRouting()
}
