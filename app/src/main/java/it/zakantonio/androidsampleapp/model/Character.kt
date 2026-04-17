package it.zakantonio.androidsampleapp.model

data class Character(
    val id: Int,
    val name: String,
    val ki: String,
    val maxKi: String,
    val race: String,
    val gender: String,
    val description: String,
    val image: String,
    val affiliation: String,
    val transformations: List<Transformation> = emptyList()
)

data class Transformation(
    val id: Int,
    val name: String,
    val image: String,
    val ki: String
)