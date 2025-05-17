package com.comppot.mindsnack.profile.data.remote.dto

data class ProfileResponseDTO(
    val newUser: Boolean,
    val account: UserDTO,
    val preferences: PreferencesDTO
)
