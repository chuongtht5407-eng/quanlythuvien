package com.example.librarymanagement.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontWeight
import com.example.librarymanagement.viewmodel.LibraryViewModel

@Composable
fun StudentListScreen(
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
            "Danh sách sinh viên",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )
        Spacer(Modifier.height(12.dp))
        Text("Chọn một sinh viên để quản lý việc mượn sách.")
        Spacer(Modifier.height(8.dp))

        LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            items(viewModel.students, key = { it.id }) { student ->
                Card(Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(Modifier.weight(1f)) {
                            Text(student.name, fontWeight = FontWeight.Bold)
                            Text("Mã sinh viên: ${student.studentCode}")
                            Text(
                                "Sách đang mượn: ${viewModel.borrowedBooksForStudent(student.id).size}"
                            )
                        }
                        Button(
                            onClick = { viewModel.selectStudent(student) },
                            enabled = viewModel.selectedStudent.id != student.id
                        ) {
                            Text(
                                if (viewModel.selectedStudent.id == student.id)
                                    "Đang chọn" else "Chọn"
                            )
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
