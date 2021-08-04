package com.vinsol.meetingscheduler.data.retrofit

import com.vinsol.meetingscheduler.models.apiresponse.ApiResponseItem
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    companion object {
        const val BASE_URL = "http://fathomless-shelf-5846.herokuapp.com"
    }

    @GET("/api/schedule")
    suspend fun getSchedule(
        @Query("date", encoded = true) date: String
    ): List<ApiResponseItem>

}