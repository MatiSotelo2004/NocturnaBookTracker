package org.matias.nocturnatracker.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.matias.nocturnatracker.domain.model.Book
import org.matias.nocturnatracker.domain.repository.BookRepository
import org.matias.nocturnatracker.data.repository.BookRepositoryImpl

data class SearchUiState(
    val query: String = "",
    val selectedGenre: String = "Todos",
    val isLoading: Boolean = false,
    val books: List<Book> = emptyList(),
    val errorMessage: String? = null
)

class SearchViewModel(
    private val repository: BookRepository = BookRepositoryImpl()
) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()

    private var searchJob: Job? = null

    init {
        loadInitialBooks()
    }

    fun onQueryChange(newQuery: String) {
        _uiState.update { it.copy(query = newQuery) }
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(450) // Debounce to protect OpenLibrary API rate limit
            executeSearch(newQuery, _uiState.value.selectedGenre)
        }
    }

    fun onGenreSelected(genre: String) {
        if (_uiState.value.selectedGenre == genre) return
        _uiState.update { it.copy(selectedGenre = genre) }
        viewModelScope.launch {
            if (genre == "Todos") {
                if (_uiState.value.query.isNotBlank()) {
                    executeSearch(_uiState.value.query, genre)
                } else {
                    loadInitialBooks()
                }
            } else {
                _uiState.update { it.copy(isLoading = true, errorMessage = null) }
                repository.getBooksByGenre(genre)
                    .onSuccess { books ->
                        _uiState.update { it.copy(isLoading = false, books = books) }
                    }
                    .onFailure { error ->
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                errorMessage = error.message ?: "No se pudieron obtener libros de $genre."
                            )
                        }
                    }
            }
        }
    }

    fun retry() {
        val current = _uiState.value
        viewModelScope.launch {
            if (current.query.isNotBlank()) {
                executeSearch(current.query, current.selectedGenre)
            } else {
                loadInitialBooks()
            }
        }
    }

    private fun loadInitialBooks() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            repository.getFeaturedBooks()
                .onSuccess { books ->
                    _uiState.update { it.copy(isLoading = false, books = books) }
                }
                .onFailure { error ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = error.message ?: "Error al conectar con la biblioteca nocturna."
                        )
                    }
                }
        }
    }

    private suspend fun executeSearch(query: String, genre: String) {
        if (query.isBlank()) {
            if (genre == "Todos") {
                loadInitialBooks()
            } else {
                onGenreSelected(genre)
            }
            return
        }

        _uiState.update { it.copy(isLoading = true, errorMessage = null) }
        repository.searchBooks(query)
            .onSuccess { books ->
                _uiState.update { it.copy(isLoading = false, books = books) }
            }
            .onFailure { error ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = error.message ?: "Error al buscar libros."
                    )
                }
            }
    }
}
