package com.example.librarymanagement.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.example.librarymanagement.viewmodel.LibraryViewModel

@Composable
fun BookListScreen(
    viewModel: LibraryViewModel,
    contentPadding: PaddingValues
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(contentPadding)
            .padding(18.dp)
    ) {
        Text(
            "Danh sách sách",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )
        Spacer(Modifier.height(12.dp))
        Text("Tổng số sách: ${viewModel.books.size}")
        Spacer(Modifier.height(8.dp))

        LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            items(viewModel.books, key = { it.id }) { book ->
                Card(Modifier.fillMaxWidth()) {
                    Column(Modifier.padding(14.dp)) {
                        Text(book.title, fontWeight = FontWeight.Bold)
                        Text("Tác giả: ${book.author}")
                        Text(
                            if (book.isBorrowed) "Trạng thái: Đã mượn"
                            else "Trạng thái: Có sẵn",
                            color = if (book.isBorrowed) Color(0xFFAD3448)
                            else Color(0xFF287A48)
                        )
                        if (book.isBorrowed) {
                            Spacer(Modifier.height(6.dp))
                            OutlinedButton(
                                onClick = { viewModel.returnBook(book.id) }
                            ) {
                                Text("Trả sách")
                            }
                        }
                    }
                }
            }
        }
        if (viewModel.message.isNotBlank()) {
            Spacer(Modifier.height(8.dp))
            Text(viewModel.message, color = MaterialTheme.colorScheme.primary)
        }
    }
}
