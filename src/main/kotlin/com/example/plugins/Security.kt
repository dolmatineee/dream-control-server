package com.example.plugins

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.example.authentication.JwtService
import com.example.data.model.RoleModel
import com.example.data.model.UserModel
import com.example.data.model.repository.UserRepositoryImpl
import com.example.domain.usecase.UserUseCase
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import kotlinx.coroutines.runBlocking

fun Application.configureSecurity() {

    val jwtService = JwtService()
    val repository = UserRepositoryImpl()
    val userUseCase = UserUseCase(repository, jwtService)

    runBlocking {
        userUseCase.createUser(
                UserModel(
                        id = 1,
                        email = "test@test.com",
                        login = "Login",
                        password = "Password",
                        isActive = true,
                        role = RoleModel.MODERATOR
                )
        )
    }


    authentication {
        jwt("jwt") {
            verifier(jwtService.getVerifier())
            realm = "Service server"
            validate {
                val payload = it.payload
                val email = payload.getClaim("email").asString()
                val user = userUseCase.findUserByEmail(email = email)
                user
            }
        }
    }
}
