package org.matias.nocturnatracker.data.mapper

import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import org.matias.nocturnatracker.data.remote.dto.OpenLibraryDocDto
import org.matias.nocturnatracker.data.remote.dto.OpenLibraryWorkDetailsDto
import org.matias.nocturnatracker.domain.model.Book

fun OpenLibraryDocDto.toDomain(): Book {
    return Book(
        id = cleanWorkId(),
        title = title,
        author = authorNames?.firstOrNull() ?: "Autor desconocido",
        coverUrl = toCoverUrl("M"),
        firstPublishYear = firstPublishYear,
        pageCount = numberOfPages ?: 300,
        rating = ratingsAverage?.let { (it * 10).toInt() / 10.0 },
        genres = subjects?.take(3) ?: emptyList()
    )
}

fun OpenLibraryWorkDetailsDto.extractDescription(): String? {
    return when (val elem = descriptionJson) {
        is JsonPrimitive -> elem.content
        is JsonObject -> (elem["value"] as? JsonPrimitive)?.content
        else -> null
    }
}
