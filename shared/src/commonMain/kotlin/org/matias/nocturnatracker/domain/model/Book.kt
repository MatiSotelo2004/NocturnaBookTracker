package org.matias.nocturnatracker.domain.model

data class Book(
    val id: String,
    val title: String,
    val author: String,
    val coverUrl: String?,
    val firstPublishYear: Int?,
    val pageCount: Int?,
    val rating: Double?,
    val genres: List<String>,
    val description: String? = null
)
