package com.comppot.mindsnack.model

import com.google.firebase.auth.FirebaseUser

data class User(
    val fullName: String = "",
    val image: String = "",
)

fun FirebaseUser.toUser() = User(
    fullName = displayName.orEmpty(),
    image = photoUrl.toString()
)
