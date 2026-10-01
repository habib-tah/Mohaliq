package com.habib.mohaliq.core.data.network.api

import com.habib.mohaliq.core.data.network.dto.ChatRequestDto
import com.habib.mohaliq.core.data.network.dto.ChatResponseDto
import retrofit2.http.Body
import retrofit2.http.POST

interface AiApi {

    // The Worker doesn't route on path — every request hits the same
    // handler regardless of what comes after the domain — so "chat" here
    // is arbitrary, just needs to be non-empty (Retrofit requires a real
    // relative path).
    @POST("chat")
    suspend fun sendChat(@Body request: ChatRequestDto): ChatResponseDto

}
