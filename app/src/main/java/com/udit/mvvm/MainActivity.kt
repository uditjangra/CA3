package com.udit.mvvm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.udit.mvvm.ui.theme.MVVMTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MVVMTheme {
                MovieScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieScreen(viewModel: MovieViewModel = viewModel()) {
    val movies by viewModel.movies.collectAsState()
    var title by rememberSaveable { mutableStateOf("") }
    var genre by rememberSaveable { mutableStateOf("") }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Movie Watchlist") }) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                placeholder = { Text("Enter movie title") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = genre,
                onValueChange = { genre = it },
                placeholder = { Text("Enter movie genre") },
                modifier = Modifier.fillMaxWidth()
            )
            Button(
                onClick = {
                    if (title.isNotBlank() && genre.isNotBlank()) {
                        viewModel.addMovie(title.trim(), genre.trim())
                        title = ""
                        genre = ""
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Add Movie")
            }

            Text("Total Movies: ${movies.size}", fontSize = 18.sp)

            if (movies.isEmpty()) {
                Text("No movies added yet")
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(movies) { movie ->
                        Card(modifier = Modifier.fillMaxWidth()) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Text(movie.title, fontSize = 18.sp)
                                Text(movie.genre)
                            }
                        }
                    }
                }
            }
        }
    }
}