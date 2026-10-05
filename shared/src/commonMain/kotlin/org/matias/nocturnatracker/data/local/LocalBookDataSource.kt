package org.matias.nocturnatracker.data.local

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.matias.nocturnatracker.domain.model.Book

class LocalBookDataSource(
    private val localStorage: PlatformLocalStorage = PlatformLocalStorage()
) {
    private val json = Json {
        ignoreUnknownKeys = true
        prettyPrint = false
    }

    private val keyGuestBooks = "guest_books_json"

    private val _booksFlow = MutableStateFlow<List<Book>>(loadBooks())
    val booksFlow: Flow<List<Book>> = _booksFlow.asStateFlow()

    private fun loadBooks(): List<Book> {
        val rawJson = localStorage.getString(keyGuestBooks) ?: return emptyList()
        return try {
            json.decodeFromString<List<Book>>(rawJson)
        } catch (_: Exception) {
            emptyList()
        }
    }

    private fun persistBooks(books: List<Book>) {
        try {
            val rawJson = json.encodeToString(books)
            localStorage.putString(keyGuestBooks, rawJson)
            _booksFlow.value = books
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun saveBookStatus(book: Book, status: String) {
        val current = loadBooks().toMutableList()
        val index = current.indexOfFirst { it.id == book.id }
        val updatedBook = book.copy(status = status)
        if (index >= 0) {
            current[index] = updatedBook
        } else {
            current.add(updatedBook)
        }
        persistBooks(current)
    }

    fun removeBook(bookId: String) {
        val current = loadBooks().toMutableList()
        current.removeAll { it.id == bookId }
        persistBooks(current)
    }
}
