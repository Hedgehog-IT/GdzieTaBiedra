package com.hedgehog.gdzietabiedra.domain

data class Shop(
    val id: String,
    val address: String,
    val distance: Double?,
    val openHours: String?
)