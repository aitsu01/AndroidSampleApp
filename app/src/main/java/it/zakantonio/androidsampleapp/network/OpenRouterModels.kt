package it.zakantonio.androidsampleapp.network

import com.squareup.moshi.Json

// ── Modelli per la RICHIESTA ──────────────────────────────────────────────────

// Corpo della richiesta POST inviata a OpenRouter
data class ChatRequest(
    val model: String,
    val messages: List<ChatMessage>,
    // @field:Json rinomina la proprietà nel JSON — "maxTokens" diventa "max_tokens"
    @field:Json(name = "max_tokens") val maxTokens: Int? = null
)

// Singolo messaggio nella conversazione
// role può essere: "system", "user", "assistant"
data class ChatMessage(
    val role: String,
    val content: String
)

// ── Modelli per la RISPOSTA ───────────────────────────────────────────────────

// Corpo della risposta ricevuta da OpenRouter
data class ChatResponse(
    val choices: List<Choice>
)

data class Choice(
    val message: ChatMessage
)
