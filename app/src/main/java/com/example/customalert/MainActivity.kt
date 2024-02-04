package com.example.customalert

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.customalert.ui.theme.AlertCyan
import com.example.customalert.ui.theme.CustomAlertTheme
import java.util.UUID

data class Book(
    val id: UUID = UUID.randomUUID(),
    val title: String = "",
    val author: String = "",
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CustomAlertTheme {
                MainScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val booksList = listOf(
        Book(title = "Atomic Habits", author = "James Clear"),
        Book(title = "Start With Why", author = "Simon Sinek"),
        Book(title = "Think Like A Monk", author = "Jay Shetty"),
        Book(title = "Limitless", author = "Jim Kwik")
    )
    val books by remember { mutableStateOf(booksList) }
    var randomBook by remember { mutableStateOf<Book?>(null) }
    val selectedBook = remember { mutableStateOf<Book?>(null) }

    val showBookAlert = remember { mutableStateOf(false) }
    val showCreditsAlert = remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = "Custom Alert Dialog") },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                ),
                actions = {
                    IconButton(onClick = { showCreditsAlert.value = true }) {
                        Icon(
                            imageVector = Icons.Filled.AccountBox,
                            contentDescription = "",
                            tint = AlertCyan
                        )
                    }
                }
            )
        },
        containerColor = MaterialTheme.colorScheme.surface
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(top = 16.dp)
                .fillMaxSize()
                .wrapContentSize(Alignment.TopCenter),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ElevatedButton(
                onClick = {
                    randomBook = books.random()
                    showBookAlert.value = true
                }
            ) {
                Text(text = "Select Random Book", color = AlertCyan)
            }

            selectedBook.value?.let {
                Row(
                    modifier = Modifier,
                    horizontalArrangement = Arrangement.spacedBy(0.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "${it.title} by ",
                        fontSize = 20.sp,
                    )
                    Text(
                        text = it.author,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                    )
                }
            }
        }

        if (showBookAlert.value) {
            // TODO: Show Book Alert
        }

        if (showCreditsAlert.value) {
            // TODO: Show Credits Alert
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CustomAlertTheme {
        MainScreen()
    }
}