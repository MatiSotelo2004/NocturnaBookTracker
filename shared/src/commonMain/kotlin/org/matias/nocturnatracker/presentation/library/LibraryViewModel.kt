package org.matias.nocturnatracker.presentation.library

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.matias.nocturnatracker.data.repository.AuthRepositoryImpl
import org.matias.nocturnatracker.data.repository.LibraryRepositoryImpl
import org.matias.nocturnatracker.domain.model.Book
import org.matias.nocturnatracker.domain.repository.AuthRepository
import org.matias.nocturnatracker.domain.repository.LibraryRepository

data class LibraryUiState(
    val books: List<Book> = emptyList(),
    val isLoading: Boolean = false
)

@OptIn(ExperimentalCoroutinesApi::class)
class LibraryViewModel(
    private val libraryRepository: LibraryRepository = LibraryRepositoryImpl(),
    private val authRepository: AuthRepository = AuthRepositoryImpl()
) : ViewModel() {

    private val _uiState = MutableStateFlow(LibraryUiState(isLoading = true))
    val uiState: StateFlow<LibraryUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            authRepository.currentUser.flatMapLatest { user ->
                libraryRepository.getUserBooks(user?.uid)
            }.collect { books ->
                _uiState.update { it.copy(books = books, isLoading = false) }
            }
        }
    }
}
