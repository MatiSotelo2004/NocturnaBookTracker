package org.matias.nocturnatracker.domain.repository

import kotlinx.coroutines.flow.Flow
import org.matias.nocturnatracker.domain.model.Book

interface LibraryRepository {
    fun getUserBooks(userId: String?): Flow<List<Book>>
    suspend fun saveBookStatus(userId: String?, book: Book, status: String): Result<Unit>
    suspend fun removeBook(userId: String?, bookId: String): Result<Unit>
}
