package com.example.utils

class Constants {

    object Role {
        const val ADMIN = "admin"
        const val MODERATOR = "moderator"
        const val CLIENT = "client"
    }


    object Error {
        const val GENERAL = "Oh, something went wrong!"
        const val WRONG_EMAIL = "Wrong email address!"
        const val INCORRECT_PASSWORD = "Incorrect password"
        const val MISSING_FIELDS = "Missing some fields"
        const val USER_NOT_FOUND = "Opps, user not found"
    }

    object Success {
        const val DREAM_ADDED_SUCCESSFULLY = "Dream added successfully"
        const val DREAM_UPDATE_SUCCESSFULLY = "Dream update successfully"
        const val DREAM_DELETED_SUCCESSFULLY = "Dream delete successfully"

    }


    object Value {
        const val ID = "id"
    }
}