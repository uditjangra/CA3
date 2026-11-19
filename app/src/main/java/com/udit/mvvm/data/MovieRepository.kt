package com.udit.mvvm.data

import kotlinx.coroutines.flow.Flow

class MovieRepository(private val dao: MovieDao) {
    val allMovies: Flow<List<Movie>> = dao.getAllMovies()

    suspend fun addMovie(movie: Movie) = dao.insert(movie)
}
