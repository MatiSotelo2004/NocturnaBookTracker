package org.matias.nocturnatracker.data.repository

import org.matias.nocturnatracker.data.mapper.extractDescription
import org.matias.nocturnatracker.data.mapper.toDomain
import org.matias.nocturnatracker.data.remote.OpenLibraryApi
import org.matias.nocturnatracker.domain.model.Book
import org.matias.nocturnatracker.domain.repository.BookRepository

class BookRepositoryImpl(
    private val api: OpenLibraryApi = OpenLibraryApi()
) : BookRepository {

    override suspend fun searchBooks(query: String): Result<List<Book>> {
        if (query.isBlank()) {
            return getFeaturedBooks()
        }
        return api.searchBooks(query).map { response ->
            response.docs.map { it.toDomain() }
        }
    }

    override suspend fun getFeaturedBooks(): Result<List<Book>> {
        // Nocturna themed initial curated query: Dark fantasy / gothic / horror literature
        return api.searchBooks("horror gothic fantasy", limit = 20).map { response ->
            response.docs.map { it.toDomain() }
        }
    }

    override suspend fun getBooksByGenre(genre: String): Result<List<Book>> {
        val subjectQuery = when (genre.lowercase()) {
            "fantasía", "fantasia" -> "fantasy"
            "terror" -> "horror"
            "thrillers", "thriller" -> "thriller"
            "sci-fi", "ciencia ficción" -> "science_fiction"
            "manga" -> "manga"
            else -> "fantasy"
        }
        return api.searchBySubject(subjectQuery, limit = 20).map { response ->
            response.docs.map { it.toDomain() }
        }
    }

    override suspend fun getBookDetails(bookId: String): Result<Book> {
        val cleanId = bookId.removePrefix("/works/").removePrefix("works/")
        return api.getWorkDetails(cleanId).map { details ->
            val coverId = details.covers?.firstOrNull()
            Book(
                id = cleanId,
                title = details.title ?: "Sin título",
                author = "Nocturna Archive",
                coverUrl = coverId?.let { "https://covers.openlibrary.org/b/id/$it-L.jpg" },
                firstPublishYear = null,
                pageCount = 350,
                rating = 4.5,
                genres = details.subjects?.take(4) ?: listOf("Literatura"),
                description = details.extractDescription() ?: "Una cautivadora historia custodiada en los archivos de la noche."
            )
        }
    }
}
