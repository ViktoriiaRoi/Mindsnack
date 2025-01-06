package com.comppot.mindsnack.profile.data.remote

import com.comppot.mindsnack.profile.data.remote.dto.ProfileResponseDTO
import com.comppot.mindsnack.profile.data.remote.dto.UserDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ProfileApi {
    @GET("/profile")
    suspend fun getUser(): Response<UserDTO>

    @POST("/profile/create")
    suspend fun createProfile(): Response<ProfileResponseDTO>

    @POST("/profile/preferences")
    suspend fun setPreferences(
        @Query("theme") themeId: Int? = null,
        @Query("notificationsEnabled") notifications: Boolean? = null
    ): Response<Unit>
}
