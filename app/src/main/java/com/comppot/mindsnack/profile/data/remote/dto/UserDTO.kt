package com.comppot.mindsnack.profile.data.remote.dto

import com.comppot.mindsnack.profile.domain.model.User

data class UserDTO(
    val uid: String,
    val email: String,
    val name: String,
    val profile_picture: String
)

fun UserDTO.toUser() = User(
    fullName = name,
    image = profile_picture
)
