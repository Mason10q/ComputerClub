package ru.mirea.computerclub.data.network

import androidx.annotation.IntRange
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import ru.mirea.computerclub.BuildConfig
import ru.mirea.computerclub.data.network.dtos.ComputerDto
import ru.mirea.computerclub.data.network.dtos.UserDto
import ru.mirea.computerclub.data.network.dtos.UserIdDto
import ru.mirea.computerclub.data.network.retrofit.EndpointUrl

@EndpointUrl(BuildConfig.ENDPOINT_URL)
interface ComputerClubApi {

    @POST("auth/signIn")
    suspend fun signIn(@Body user: UserDto): Response<UserIdDto>

    @POST("auth/signUp")
    suspend fun signUp(@Body user: UserDto): Response<UserIdDto>

    @POST("auth/profile/remove")
    suspend fun removeProfile(@Query("user_id") userId: Int)

    @GET("computers/list")
    suspend fun getAllComputers(
        @Query("page") @IntRange(from = 1) page: Int = 1,
        @Query("limit") @IntRange(from = 1, to = MAX_PAGE_SIZE.toLong()) pageSize: Int = DEFAULT_PAGE_SIZE
    ): Response<List<ComputerDto>>

    @GET("computers/search")
    suspend fun searchComputers(
        @Query("query") query: String,
        @Query("page") @IntRange(from = 1) page: Int = 1,
        @Query("limit") @IntRange(from = 1, to = MAX_PAGE_SIZE.toLong()) pageSize: Int = DEFAULT_PAGE_SIZE
    ): Response<List<ComputerDto>>


    companion object {
        const val DEFAULT_PAGE_SIZE = 10
        const val MAX_PAGE_SIZE = 20
    }
}