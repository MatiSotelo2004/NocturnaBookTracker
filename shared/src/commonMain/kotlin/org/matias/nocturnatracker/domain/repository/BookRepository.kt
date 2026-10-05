package org.matias.nocturnatracker.domain.repository

import org.matias.nocturnatracker.domain.model.Book

interface BookRepository {
    suspend fun searchBooks(query: String): Result<List<Book>>
    suspend fun getFeaturedBooks(): Result<List<Book>>
    suspend fun getBooksByGenre(genre: String): Result<List<Book>>
    suspend fun getBookDetails(bookId: String): Result<Book>
}
