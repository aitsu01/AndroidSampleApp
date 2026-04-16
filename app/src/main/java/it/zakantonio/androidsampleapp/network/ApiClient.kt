package it.zakantonio.androidsampleapp.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

// Singleton che configura e fornisce il servizio Retrofit per la DragonBall API.
// Pattern Singleton (object in Kotlin): una sola istanza condivisa in tutta l'app.
object ApiClient {

    // URL base dell'API pubblica Dragon Ball (nessuna autenticazione richiesta)
    private const val BASE_URL = "https://dragonball-api.com/api/"

    // Moshi converte oggetti Kotlin ↔ JSON
    private val moshi: Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    // OkHttpClient gestisce le connessioni HTTP
    private val okHttpClient: OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(10, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
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

    // Implementazione dell'interfaccia DragonBallService generata da Retrofit.
    // Questo è l'oggetto che il ViewModel usa per fare le chiamate.
    val service: DragonBallService = retrofit.create(DragonBallService::class.java)
}
