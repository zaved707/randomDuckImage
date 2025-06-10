package com.zavedahmad.randomduck

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImagePainter
import coil3.compose.rememberAsyncImagePainter
import com.zavedahmad.randomduck.ui.theme.RandomDuckTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: DuckViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()


        setContent {
            val link by viewModel.link.collectAsStateWithLifecycle()
            val painter = rememberAsyncImagePainter(link)
            val state by painter.state.collectAsStateWithLifecycle()
            LaunchedEffect(Unit) { viewModel.getLinkToImageRandom()}
            RandomDuckTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(modifier = Modifier.padding(innerPadding).fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                        OutlinedCard (modifier = Modifier.size(200.dp) ) {
                            Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxSize()) {
                           when(state){
                               is AsyncImagePainter.State.Empty-> {Text("Empty")}
                               is AsyncImagePainter.State.Loading ->{
                                   CircularProgressIndicator()
                               }
                               is AsyncImagePainter.State.Success -> {
                                   Image(modifier = Modifier.fillMaxSize(),
                                       painter = painter,
                                       contentDescription = "duck"
                                   )
                               }
                               else -> {Text("No Image Loaded")}
                           }}
                        }

                        Text(link)
                        Button(onClick = { viewModel.getLinkToImageRandom() }) { Text("getData") }
                    }
                }
            }
        }
    }
}


