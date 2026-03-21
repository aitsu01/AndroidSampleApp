package it.zakantonio.androidsampleapp.model

// Rappresenta un singolo messaggio nella chat.
// Il tipo determina se il messaggio è stato scritto dall'utente o dal bot.
data class Message(
    val testo: String,
    val tipo: TipoMessaggio
)

// Enum che distingue i due mittenti possibili
enum class TipoMessaggio {
    UTENTE,
    BOT
}
