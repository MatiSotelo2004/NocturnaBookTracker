package org.matias.nocturnatracker.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.matias.nocturnatracker.data.repository.AuthRepositoryImpl
import org.matias.nocturnatracker.data.repository.BookRepositoryImpl
import org.matias.nocturnatracker.data.repository.LibraryRepositoryImpl
import org.matias.nocturnatracker.domain.model.Book
import org.matias.nocturnatracker.domain.repository.AuthRepository
import org.matias.nocturnatracker.domain.repository.BookRepository
import org.matias.nocturnatracker.domain.repository.LibraryRepository

data class DetailUiState(
    val isLoading: Boolean = true,
    val book: Book? = null,
    val errorMessage: String? = null,
    val isSaved: Boolean = false,
    val readingStatus: String = "Por Leer" // "Por Leer", "Leyendo", "Completado"
)

class DetailViewModel(
    private val bookId: String,
    private val repository: BookRepository = BookRepositoryImpl(),
    private val libraryRepository: LibraryRepository = LibraryRepositoryImpl(),
    private val authRepository: AuthRepository = AuthRepositoryImpl()
) : ViewModel() {

    private val _uiState = MutableStateFlow(DetailUiState())
    val uiState: StateFlow<DetailUiState> = _uiState.asStateFlow()

    private var currentUserId: String? = null

    init {
        viewModelScope.launch {
            authRepository.currentUser.collect { user ->
                currentUserId = user?.uid
                checkSavedStatus()
            }
        }
        loadBook()
    }

    fun loadBook() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            repository.getBookDetails(bookId)
                .onSuccess { book ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            book = book
                        )
                    }
                    checkSavedStatus()
                }
                .onFailure { error ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = error.message ?: "No se pudieron obtener los detalles del libro."
                        )
                    }
                }
        }
    }

    private fun checkSavedStatus() {
        viewModelScope.launch {
            libraryRepository.getUserBooks(currentUserId).collect { savedBooks ->
                val savedBook = savedBooks.find { it.id == bookId }
                if (savedBook != null) {
                    _uiState.update {
                        it.copy(
                            isSaved = true,
                            readingStatus = savedBook.status ?: "Por Leer"
                        )
                    }
                } else {
                    _uiState.update {
                        it.copy(isSaved = false)
                    }
                }
            }
        }
    }

    fun setReadingStatus(status: String) {
        val currentBook = _uiState.value.book ?: return
        _uiState.update { it.copy(readingStatus = status, isSaved = true) }
        viewModelScope.launch {
            libraryRepository.saveBookStatus(currentUserId, currentBook, status)
        }
    }

    fun toggleSaved() {
        val currentBook = _uiState.value.book ?: return
        val currentlySaved = _uiState.value.isSaved
        if (currentlySaved) {
            _uiState.update { it.copy(isSaved = false) }
            viewModelScope.launch {
                libraryRepository.removeBook(currentUserId, currentBook.id)
            }
        } else {
            val status = _uiState.value.readingStatus
            _uiState.update { it.copy(isSaved = true) }
            viewModelScope.launch {
                libraryRepository.saveBookStatus(currentUserId, currentBook, status)
            }
        }
    }
}
