package com.udit.mvvm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.udit.mvvm.data.Movie
import com.udit.mvvm.data.MovieDatabase
import com.udit.mvvm.data.MovieRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MovieViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = MovieRepository(MovieDatabase.getDatabase(application).movieDao())

    val movies: StateFlow<List<Movie>> = repository.allMovies
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun addMovie(title: String, genre: String) {
        viewModelScope.launch {
            repository.addMovie(Movie(title = title, genre = genre))
        }
    }
}
