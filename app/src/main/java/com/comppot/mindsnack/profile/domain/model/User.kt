package com.comppot.mindsnack.profile.domain.model

import com.google.firebase.auth.FirebaseUser
import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("fullName")
    val fullName: String = "",
    @SerializedName("image")
    val image: String = "",
)

fun FirebaseUser.toUser() = User(
    fullName = displayName.orEmpty(),
    image = photoUrl.toString()
)
