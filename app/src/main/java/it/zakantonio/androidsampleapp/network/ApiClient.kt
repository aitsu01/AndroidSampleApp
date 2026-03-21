package it.zakantonio.androidsampleapp.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Singleton che configura e fornisce l'istanza di Retrofit.
 *
 * Questo oggetto (object in Kotlin = singleton) ha il compito di:
 * - Configurare OkHttp per le richieste HTTP
 * - Configurare Moshi per il parsing JSON
 * - Creare l'istanza di Retrofit
 * - Fornire l'interfaccia API
 *
 * Pattern Singleton: una sola istanza condivisa in tutta l'app.
 */
object ApiClient {

    // URL base dell'API Deck of Cards
    private const val BASE_URL = "https://openrouter.ai/api/v1/chat/completions"

    /**
     * Configura Moshi per il parsing JSON.
     *
     * Moshi converte JSON in oggetti Kotlin e viceversa.
     * KotlinJsonAdapterFactory aggiunge supporto per data class Kotlin.
     */
    private val moshi: Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    /**
     * Configura OkHttpClient per le richieste HTTP.
     *
     * OkHttpClient gestisce:
     * - Timeout delle richieste
     * - Interceptor per logging
     * - Cache e gestione connessioni
     */
    private val okHttpClient: OkHttpClient = OkHttpClient.Builder()
        // Timeout per stabilire la connessione (10 secondi)
        .connectTimeout(10, TimeUnit.SECONDS)
        // Timeout per leggere la risposta (30 secondi)
        .readTimeout(30, TimeUnit.SECONDS)
        // Timeout per scrivere la richiesta (15 secondi)
        .writeTimeout(15, TimeUnit.SECONDS)
        // Interceptor per loggare richieste e risposte (utile per debug)
        .addInterceptor(
            HttpLoggingInterceptor().apply {
                // BODY logga tutto: URL, headers, body
                // In produzione usare NONE o BASIC
                level = HttpLoggingInterceptor.Level.BODY
            }
        )
        .build()

    /**
     * Istanza di Retrofit configurata.
     *
     * Retrofit è il client REST che:
     * - Fa le chiamate HTTP tramite OkHttp
     * - Converte JSON tramite Moshi
     * - Trasforma le interfacce in chiamate reali
     */
    private val retrofit: Retrofit = Retrofit.Builder()
        // URL base per tutte le chiamate
        .baseUrl(BASE_URL)
        // Client HTTP da usare
        .client(okHttpClient)
        // Converter per JSON
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

}
