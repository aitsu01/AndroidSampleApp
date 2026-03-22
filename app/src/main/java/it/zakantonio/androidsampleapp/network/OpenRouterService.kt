package it.zakantonio.androidsampleapp.network

import retrofit2.http.Body
import retrofit2.http.POST

// Interfaccia Retrofit: descrive gli endpoint dell'API come funzioni Kotlin.
// Retrofit genera automaticamente l'implementazione a runtime.
// "suspend" indica che è una funzione per coroutine — non blocca il thread chiamante.
interface OpenRouterService {

    @POST("chat/completions")
    suspend fun inviaMessaggio(@Body richiesta: ChatRequest): ChatResponse
}
