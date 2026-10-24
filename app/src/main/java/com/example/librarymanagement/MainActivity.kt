package com.example.librarymanagement

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color
import com.example.librarymanagement.ui.LibraryApp

private val LibraryColors = lightColorScheme(
    primary = Color(0xFF2856A6),
    secondary = Color(0xFFAD3448),
    background = Color(0xFFFFFFFF),
    surface = Color(0xFFFFFFFF)
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme(colorScheme = LibraryColors) {
                Surface {
                    LibraryApp()
                }
            }
        }
    }
}

@androidx.compose.ui.tooling.preview.Preview(showBackground = true)
@androidx.compose.runtime.Composable
fun MainActivityPreview() {
    MaterialTheme(colorScheme = LibraryColors) {
        Surface {
            LibraryApp()
        }
    }
}

