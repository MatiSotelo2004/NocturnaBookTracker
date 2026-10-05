package org.matias.nocturnatracker.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OpenLibrarySearchResponseDto(
    @SerialName("numFound") val numFound: Int = 0,
    @SerialName("docs") val docs: List<OpenLibraryDocDto> = emptyList()
)

@Serializable
data class OpenLibraryDocDto(
    @SerialName("key") val key: String,
    @SerialName("title") val title: String,
    @SerialName("author_name") val authorNames: List<String>? = null,
    @SerialName("first_publish_year") val firstPublishYear: Int? = null,
    @SerialName("cover_i") val coverId: Long? = null,
    @SerialName("subject") val subjects: List<String>? = null,
    @SerialName("number_of_pages_median") val numberOfPages: Int? = null,
    @SerialName("ratings_average") val ratingsAverage: Double? = null
) {
    fun toCoverUrl(size: String = "M"): String? {
        return coverId?.let { "https://covers.openlibrary.org/b/id/$it-$size.jpg" }
    }

    fun cleanWorkId(): String {
        return key.removePrefix("/works/").removePrefix("works/")
    }
}

@Serializable
data class OpenLibraryWorkDetailsDto(
    @SerialName("key") val key: String? = null,
    @SerialName("title") val title: String? = null,
    @SerialName("description") val descriptionJson: kotlinx.serialization.json.JsonElement? = null,
    @SerialName("covers") val covers: List<Long>? = null,
    @SerialName("subjects") val subjects: List<String>? = null
)
