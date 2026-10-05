package org.matias.nocturnatracker.data.repository

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.firestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.matias.nocturnatracker.data.local.LocalBookDataSource
import org.matias.nocturnatracker.domain.model.Book
import org.matias.nocturnatracker.domain.repository.LibraryRepository

class LibraryRepositoryImpl(
    private val localBookDataSource: LocalBookDataSource = LocalBookDataSource()
) : LibraryRepository {
    private val firestore = Firebase.firestore

    override fun getUserBooks(userId: String?): Flow<List<Book>> {
        if (userId.isNullOrBlank()) {
            return localBookDataSource.booksFlow
        }
        return firestore.collection("users")
            .document(userId)
            .collection("books")
            .snapshots()
            .map { snapshot ->
                snapshot.documents.mapNotNull { doc ->
                    try {
                        doc.data<Book>()
                    } catch (e: Exception) {
                        null
                    }
                }
            }
    }

    override suspend fun saveBookStatus(userId: String?, book: Book, status: String): Result<Unit> = runCatching {
        if (userId.isNullOrBlank()) {
            localBookDataSource.saveBookStatus(book, status)
        } else {
            val updatedBook = book.copy(status = status)
            firestore.collection("users")
                .document(userId)
                .collection("books")
                .document(book.id)
                .set(updatedBook)
        }
    }

    override suspend fun removeBook(userId: String?, bookId: String): Result<Unit> = runCatching {
        if (userId.isNullOrBlank()) {
            localBookDataSource.removeBook(bookId)
        } else {
            firestore.collection("users")
                .document(userId)
                .collection("books")
                .document(bookId)
                .delete()
        }
    }
}
