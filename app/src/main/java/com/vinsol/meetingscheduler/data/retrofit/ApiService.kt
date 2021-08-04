package com.vinsol.meetingscheduler.data.retrofit

import com.vinsol.meetingscheduler.models.apiresponse.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    companion object {
        const val BASE_URL = "http://fathomless-shelf-5846.herokuapp.com"
    }

    @GET("/api/schedule")
    suspend fun getSchedule(
        @Query("date") date: String
    ): ApiResponse

}