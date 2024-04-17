package com.example.routes

import com.example.data.model.DreamModel
import com.example.data.model.UserModel
import com.example.data.model.requests.AddDreamRequest
import com.example.data.model.response.BaseResponse
import com.example.domain.usecase.DreamUseCase
import com.example.utils.Constants
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.dreamsRoute(dreamUseCase: DreamUseCase) {

    authenticate("jwt") {

        get("api/v1/get-all-dreams") {
            try {
                val dreams = dreamUseCase.getAllDreams()
                call.respond(HttpStatusCode.OK, dreams)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.Conflict, BaseResponse(false, e.message ?: Constants.Error.GENERAL))
            }
        }


        post("api/v1/create-dream") {
            val dreamRequest = call.receiveNullable<AddDreamRequest>() ?: kotlin.run {
                call.respond(HttpStatusCode.BadRequest, BaseResponse(false, Constants.Error.MISSING_FIELDS))
                return@post
            }

            try {
                val dream = DreamModel(
                    id = 0,
                    owner = call.principal<UserModel>()!!.id,
                    dreamTitle = dreamRequest.dreamTitle,
                    dreamDescription = dreamRequest.dreamDescription,
                    dreamDate = dreamRequest.dreamDate,
                    isVerified = dreamRequest.isVerified,
                )

                dreamUseCase.addDream(dream = dream)
                call.respond(HttpStatusCode.OK, BaseResponse(success = true, message = Constants.Success.DREAM_ADDED_SUCCESSFULLY))
            } catch (e: Exception) {
                call.respond(HttpStatusCode.Conflict, BaseResponse(false, e.message ?: Constants.Error.GENERAL))
            }
        }

        post("api/v1/update-dream") {
            val dreamRequest = call.receiveNullable<AddDreamRequest>() ?: kotlin.run {
                call.respond(HttpStatusCode.BadRequest, BaseResponse(false, Constants.Error.MISSING_FIELDS))
                return@post
            }

            try {
                val ownerId = call.principal<UserModel>()!!.id
                val dream = DreamModel(
                    id = dreamRequest.id ?: 0,
                    owner = ownerId,
                    dreamTitle = dreamRequest.dreamTitle,
                    dreamDescription = dreamRequest.dreamDescription,
                    dreamDate = dreamRequest.dreamDate,
                    isVerified = dreamRequest.isVerified,
                )

                dreamUseCase.updateDream(dream = dream, ownerId = ownerId)
                call.respond(HttpStatusCode.OK, BaseResponse(success = true, message = Constants.Success.DREAM_UPDATE_SUCCESSFULLY))
            } catch (e: Exception) {
                call.respond(HttpStatusCode.Conflict, BaseResponse(false, e.message ?: Constants.Error.GENERAL))
            }
        }

        delete("api/v1/delete-dream") {
            val dreamRequest = call.request.queryParameters[Constants.Value.ID]?.toInt() ?: kotlin.run {
                call.respond(HttpStatusCode.BadRequest, BaseResponse(false, Constants.Error.MISSING_FIELDS))
                return@delete
            }

            try {
                val ownerId = call.principal<UserModel>()!!.id

                dreamUseCase.deleteDream(dreamId = dreamRequest, ownerId = ownerId)
                call.respond(HttpStatusCode.OK, BaseResponse(success = true, message = Constants.Success.DREAM_DELETED_SUCCESSFULLY))
            } catch (e: Exception) {
                call.respond(HttpStatusCode.Conflict, BaseResponse(false, e.message ?: Constants.Error.GENERAL))
            }
        }

    }

}