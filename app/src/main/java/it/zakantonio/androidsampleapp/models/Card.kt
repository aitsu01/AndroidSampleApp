package it.zakantonio.androidsampleapp.models

import androidx.annotation.DrawableRes

/**
 * Data class che rappresenta una carta da gioco.
 *
 * Una data class in Kotlin è una classe speciale per contenere dati.
 * Il compilatore genera automaticamente:
 * - equals() e hashCode() per confrontare oggetti
 * - toString() per stampare l'oggetto in modo leggibile
 * - copy() per creare copie modificate dell'oggetto
 * - componentN() per destructuring (es. val (value, suit) = card)
 *
 * @param value Il valore della carta (es. "Asso", "Re", "10", ecc.)
 * @param suit Il seme della carta (es. "Cuori", "Picche", "Quadri", "Fiori")
 * @param icon Drawable che rappresenta il seme
 */
data class Card(
    val value: String,
    val suit: String,
    @DrawableRes
    val icon: Int
) {
    /**
     * Restituisce il nome completo della carta.
     * Esempio: "Asso di Cuori"
     */
    fun getFullName(): String {
        return "$value di $suit"
    }
}
