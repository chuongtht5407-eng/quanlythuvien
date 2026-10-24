package com.example.librarymanagement.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.librarymanagement.data.LibraryRepository
import com.example.librarymanagement.data.model.Book
import com.example.librarymanagement.data.model.Student

class LibraryViewModel : ViewModel() {
    private val repository = LibraryRepository()

    var books by mutableStateOf(repository.getBooks())
        private set
    var students by mutableStateOf(repository.getStudents())
        private set
    var selectedStudent by mutableStateOf(students.first())
        private set
    var selectedBookIds by mutableStateOf(setOf<Int>())
        private set
    var message by mutableStateOf("")
        private set

    fun selectStudent(student: Student) {
        selectedStudent = student
        message = "Đã chọn ${student.name}"
    }

    fun toggleBook(bookId: Int, checked: Boolean) {
        selectedBookIds = if (checked) selectedBookIds + bookId
        else selectedBookIds - bookId
    }

    fun borrowSelectedBooks() {
        if (selectedBookIds.isEmpty()) {
            message = "Vui lòng chọn ít nhất một cuốn sách."
            return
        }

        var successCount = 0
        selectedBookIds.forEach { id ->
            if (repository.borrowBook(selectedStudent.id, id)) successCount++
        }
        books = repository.getBooks()
        selectedBookIds = emptySet()
        message = if (successCount > 0) {
            "Đã mượn thành công $successCount cuốn sách."
        } else {
            "Không thể mượn sách đã được người khác mượn."
        }
    }

    fun addBook(title: String, author: String) {
        if (!repository.addBook(title, author)) {
            message = "Tên sách và tác giả không được để trống."
            return
        }
        books = repository.getBooks()
        message = "Thêm sách thành công."
    }

    fun returnBook(bookId: Int) {
        message = if (repository.returnBook(bookId)) {
            books = repository.getBooks()
            "Trả sách thành công."
        } else {
            "Không thể trả sách này."
        }
    }

    fun dismissMessage() {
        message = ""
    }

    fun borrowedBooksForStudent(studentId: Int): List<Book> =
        repository.getBorrowedBooksByStudent(studentId)

    fun borrowedBooksForSelectedStudent(): List<Book> =
        borrowedBooksForStudent(selectedStudent.id)
}
