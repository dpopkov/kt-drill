package learn.kt4j.m0102t01tracker

import java.time.LocalDateTime

/**
 * Модель данных.
 */
data class Item(
    val name: String,
    var id: Int? = null,
    val created: LocalDateTime = LocalDateTime.now(),
)
