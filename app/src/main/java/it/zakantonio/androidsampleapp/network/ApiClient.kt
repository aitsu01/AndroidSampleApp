package it.zakantonio.androidsampleapp.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import it.zakantonio.androidsampleapp.CONST_API_KEY
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

// Singleton che configura e fornisce il servizio Retrofit per OpenRouter.
// Pattern Singleton (object in Kotlin): una sola istanza condivisa in tutta l'app.
object ApiClient {

    // ⚠️ Sostituisci con la tua chiave API di OpenRouter (openrouter.ai/keys)
    private const val API_KEY = CONST_API_KEY

    // URL base: Retrofit aggiunge il percorso dell'endpoint definito in OpenRouterService
    private const val BASE_URL = "https://openrouter.ai/api/v1/"

    // Moshi converte oggetti Kotlin ↔ JSON
    private val moshi: Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    // OkHttpClient gestisce le connessioni HTTP
    private val okHttpClient: OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(10, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(15, TimeUnit.SECONDS)
        // Interceptor di autorizzazione: aggiunge l'header "Authorization" a ogni richiesta.
        // È più pulito di passare il token manualmente a ogni chiamata.
        .addInterceptor { chain ->
            val richiesta = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer $API_KEY")
                .build()
            chain.proceed(richiesta)
        }
        // Interceptor per il logging: logga richieste e risposte nel Logcat (utile per debug)
        .addInterceptor(
            HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
        )
        .build()

    // Istanza di Retrofit configurata con URL base, client HTTP e converter JSON
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    // Implementazione dell'interfaccia OpenRouterService generata da Retrofit.
    // Questo è l'oggetto che il ViewModel usa per fare le chiamate.
    val service: OpenRouterService = retrofit.create(OpenRouterService::class.java)
}
