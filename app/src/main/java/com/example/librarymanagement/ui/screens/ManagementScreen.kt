package com.example.librarymanagement.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.librarymanagement.viewmodel.LibraryViewModel

private val Blue = Color(0xFF2856A6)
private val SoftGray = Color(0xFFF0F0F0)
private val Red = Color(0xFFAD3448)

@Composable
fun ManagementScreen(
    viewModel: LibraryViewModel,
    contentPadding: PaddingValues
) {
    var showStudentPicker by remember { mutableStateOf(false) }
    var showAddDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(contentPadding)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 18.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Hệ thống\nQuản lý Thư viện",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(32.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(Modifier.weight(1f)) {
                Text("Sinh viên", fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(8.dp))
                OutlinedButton(
                    onClick = { showStudentPicker = true },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        viewModel.selectedStudent.name,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
            Spacer(Modifier.width(10.dp))
            Button(
                onClick = { showStudentPicker = true },
                modifier = Modifier.padding(top = 24.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Blue)
            ) {
                Text("Thay đổi")
            }
        }

        Spacer(Modifier.height(22.dp))
        Text(
            "Danh sách sách",
            modifier = Modifier.fillMaxWidth(),
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(Modifier.height(10.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 150.dp, max = 300.dp)
                .clip(RoundedCornerShape(14.dp))
                .padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            viewModel.books.forEach { book ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable(enabled = !book.isBorrowed) {
                                viewModel.toggleBook(
                                    book.id,
                                    book.id !in viewModel.selectedBookIds
                                )
                            }
                            .padding(horizontal = 12.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = book.id in viewModel.selectedBookIds,
                            onCheckedChange = {
                                viewModel.toggleBook(book.id, it)
                            },
                            enabled = !book.isBorrowed,
                            colors = CheckboxDefaults.colors(checkedColor = Red)
                        )
                        Column(Modifier.weight(1f)) {
                            Text(book.title, fontWeight = FontWeight.Medium)
                            Text(
                                if (book.isBorrowed) "Đã được mượn" else book.author,
                                style = MaterialTheme.typography.bodySmall,
                                color = if (book.isBorrowed) Red else Color.Gray
                            )
                        }
                    }
                }
            }
        }

        Spacer(Modifier.height(18.dp))
        Button(
            onClick = { viewModel.borrowSelectedBooks() },
            modifier = Modifier.widthIn(min = 160.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Blue),
            shape = RoundedCornerShape(10.dp)
        ) {
            Text("Thêm")
        }

        if (viewModel.message.isNotBlank()) {
            Spacer(Modifier.height(12.dp))
            Text(
                viewModel.message,
                color = Blue,
                textAlign = TextAlign.Center
            )
        }

        Spacer(Modifier.height(14.dp))
        OutlinedButton(onClick = { showAddDialog = true }) {
            Text("Thêm sách mới")
        }
    }

    if (showStudentPicker) {
        AlertDialog(
            onDismissRequest = { showStudentPicker = false },
            title = { Text("Chọn sinh viên") },
            text = {
                Column {
                    viewModel.students.forEach { student ->
                        TextButton(
                            onClick = {
                                viewModel.selectStudent(student)
                                showStudentPicker = false
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(Modifier.fillMaxWidth()) {
                                Text(student.name)
                                Text(
                                    student.studentCode,
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showStudentPicker = false }) {
                    Text("Đóng")
                }
            }
        )
    }

    if (showAddDialog) {
        AddBookDialog(
            onDismiss = { showAddDialog = false },
            onAdd = { title, author ->
                viewModel.addBook(title, author)
                showAddDialog = false
            }
        )
    }
}

@Composable
private fun AddBookDialog(
    onDismiss: () -> Unit,
    onAdd: (String, String) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var author by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Thêm sách mới") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Tên sách") },
                    singleLine = true
                )
                OutlinedTextField(
                    value = author,
                    onValueChange = { author = it },
                    label = { Text("Tác giả") },
                    singleLine = true
                )
            }
        },
        confirmButton = {
            Button(onClick = { onAdd(title, author) }) {
                Text("Thêm")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Hủy") }
        }
    )
}
