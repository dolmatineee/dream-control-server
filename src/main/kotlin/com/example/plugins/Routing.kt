package com.example.plugins

import com.example.domain.usecase.DreamUseCase
import com.example.domain.usecase.UserUseCase
import com.example.routes.dreamsRoute
import com.example.routes.userRoute
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting(userUseCase: UserUseCase, dreamUseCase: DreamUseCase) {

    routing {
        userRoute(userUseCase = userUseCase)
        dreamsRoute(dreamUseCase)
    }
}
