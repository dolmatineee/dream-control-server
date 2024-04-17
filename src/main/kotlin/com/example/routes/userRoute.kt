package com.example.routes

import com.example.authentication.hash
import com.example.data.model.UserModel
import com.example.data.model.getRoleByString
import com.example.data.model.requests.LoginRequest
import com.example.data.model.requests.RegisterRequest
import com.example.data.model.response.BaseResponse
import com.example.domain.usecase.UserUseCase
import com.example.utils.Constants
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*


fun Route.userRoute(userUseCase: UserUseCase) {

    val hashFunction = { p: String  -> hash(password = p) }

    post("api/v1/signup") {
        val registerRequest = call.receiveNullable<RegisterRequest>() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest, BaseResponse(false, Constants.Error.GENERAL))
            return@post
        }

        try {
            val user = UserModel(
                id = 0,
                email = registerRequest.email.trim().lowercase(),
                login = registerRequest.login.trim().lowercase(),
                password = hashFunction(registerRequest.password.trim()),
                role = registerRequest.role.trim().getRoleByString()
            )

            userUseCase.createUser(user)
            call.respond(HttpStatusCode.OK, BaseResponse(true, userUseCase.generateToken(userModel = user)))
        } catch (e: Exception) {
            call.respond(HttpStatusCode.Conflict, BaseResponse(false, e.message ?: Constants.Error.GENERAL))
        }
    }


    post("api/v1/login") {
        val loginRequest = call.receiveNullable<LoginRequest>() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest, BaseResponse(false, Constants.Error.GENERAL))
            return@post
        }

        try {
            val user = userUseCase.findUserByEmail(loginRequest.email.trim().lowercase())

            if (user == null) {
                call.respond(HttpStatusCode.BadRequest, BaseResponse(false, Constants.Error.WRONG_EMAIL))
            } else {
                if (user.password == hashFunction(loginRequest.password)) {
                    call.respond(HttpStatusCode.OK, BaseResponse(true, userUseCase.generateToken(userModel = user)))
                } else {
                    call.respond(HttpStatusCode.BadRequest, BaseResponse(false, Constants.Error.INCORRECT_PASSWORD))
                }
            }
        } catch (e: Exception) {
            call.respond(HttpStatusCode.Conflict, BaseResponse(false, e.message ?: Constants.Error.GENERAL))
        }
    }

    authenticate("jwt") {

        get("api/v1/get-user-info") {
            try {
                val user = call.principal<UserModel>()

                if (user != null) {
                    call.respond(HttpStatusCode.OK, user)
                } else {
                    call.respond(HttpStatusCode.Conflict, BaseResponse(false, Constants.Error.USER_NOT_FOUND))
                }
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, BaseResponse(false, Constants.Error.GENERAL))
            }
        }

    }
}