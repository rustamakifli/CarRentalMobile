package com.example.myapplication

data class Car(
    val id: Int,
    val name: String,
    val brand: String,
    val model: String,
    val year: String,
    val color: String,
    val fuel: String,
    val driveUnit: String?,
    val engine: String,
    val transmission: String,
    val car_images: List<CarImage> = emptyList(),
    val carregion: String,
    val price: String,
    val priceCurrency: String,
    val carComments: List<String>
)

data class CarImage(
    val id: Int,
    val image: String,
    val isMain: Boolean
)
