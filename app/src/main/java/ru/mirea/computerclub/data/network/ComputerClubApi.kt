package ru.mirea.computerclub.data

import retrofit2.http.Body
import retrofit2.http.POST
import ru.mirea.computerclub.BuildConfig
import ru.mirea.computerclub.data.network.UserDto
import ru.mirea.computerclub.data.network.retrofit.EndpointUrl

@EndpointUrl(BuildConfig.ENDPOINT_URL + "/auth/")
interface ComputerClubApi {

    @POST("signIn")
    fun signIn(@Body user: UserDto)

}