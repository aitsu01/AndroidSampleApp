package it.zakantonio.androidsampleapp.network

import it.zakantonio.androidsampleapp.model.Character

// Risposta paginata dell'endpoint GET /characters
data class CharactersResponse(
    val items: List<Character>,
    val meta: PaginationMeta
)

// Metadati di paginazione
data class PaginationMeta(
    val totalItems: Int,
    val itemCount: Int,
    val itemsPerPage: Int,
    val totalPages: Int,
    val currentPage: Int
)
