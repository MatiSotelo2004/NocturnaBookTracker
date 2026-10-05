package org.matias.nocturnatracker.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.matias.nocturnatracker.data.repository.BookRepositoryImpl
import org.matias.nocturnatracker.domain.model.Book
import org.matias.nocturnatracker.domain.repository.BookRepository

data class DetailUiState(
    val isLoading: Boolean = true,
    val book: Book? = null,
    val errorMessage: String? = null,
    val isSaved: Boolean = false,
    val readingStatus: String = "Por Leer", // "Por Leer", "Leyendo", "Completado"
    val currentPage: Int = 0
)

class DetailViewModel(
    private val bookId: String,
    private val repository: BookRepository = BookRepositoryImpl()
) : ViewModel() {

    private val _uiState = MutableStateFlow(DetailUiState())
    val uiState: StateFlow<DetailUiState> = _uiState.asStateFlow()

    init {
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

    fun setReadingStatus(status: String) {
        _uiState.update { it.copy(readingStatus = status, isSaved = true) }
    }

    fun updateProgress(page: Int) {
        _uiState.update { it.copy(currentPage = page) }
    }

    fun toggleSaved() {
        _uiState.update { it.copy(isSaved = !it.isSaved) }
    }
}
