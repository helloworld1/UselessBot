package org.liberty.android.uselessbot

import org.liberty.android.uselessbot.model.QueryResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import retrofit2.http.Url

interface ApiAiService {
    @GET("/v1/query")
    @Headers("Authorization: Bearer ")
    fun query(@Query("query") query: String,
              @Query("lang") lang: String,
              @Query("sessionId") sessionId: String)
            : Call<QueryResponse>
}
