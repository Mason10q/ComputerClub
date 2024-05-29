package ru.mirea.computerclub.data.network

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query
import ru.mirea.computerclub.BuildConfig
import ru.mirea.computerclub.data.network.dtos.UserDto
import ru.mirea.computerclub.data.network.dtos.UserIdDto
import ru.mirea.computerclub.data.network.retrofit.EndpointUrl

@EndpointUrl(BuildConfig.ENDPOINT_URL + "/auth/")
interface ComputerClubApi {

    @POST("signIn")
    suspend fun signIn(@Body user: UserDto): Response<UserIdDto>

    @POST("signUp")
    suspend fun signUp(@Body user: UserDto): Response<UserIdDto>

    @POST("profile/remove")
    suspend fun removeProfile(@Query("user_id") userId: Int)

}