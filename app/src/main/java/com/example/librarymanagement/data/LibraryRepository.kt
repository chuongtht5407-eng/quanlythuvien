package com.example.librarymanagement.data

import com.example.librarymanagement.data.model.Book
import com.example.librarymanagement.data.model.BorrowRecord
import com.example.librarymanagement.data.model.Student
import java.time.LocalDate

class LibraryRepository {
    private val books = mutableListOf(
        Book(1, "Sách 01", "Nguyễn Văn A"),
        Book(2, "Sách 02", "Trần Văn B"),
        Book(3, "Lập trình Kotlin", "Lê Văn C"),
        Book(4, "Cơ sở dữ liệu", "Phạm Văn D")
    )

    private val students = mutableListOf(
        Student(1, "Nguyen Van A", "SV001"),
        Student(2, "Tran Thi B", "SV002"),
        Student(3, "Le Van C", "SV003")
    )

    private val borrowRecords = mutableListOf<BorrowRecord>()

    fun getBooks(): List<Book> = books.toList()
    fun getStudents(): List<Student> = students.toList()
    fun getBorrowRecords(): List<BorrowRecord> = borrowRecords.toList()

    fun addBook(title: String, author: String): Boolean {
        if (title.isBlank() || author.isBlank()) return false
        val nextId = (books.maxOfOrNull { it.id } ?: 0) + 1
        books.add(Book(nextId, title.trim(), author.trim()))
        return true
    }

    fun borrowBook(studentId: Int, bookId: Int): Boolean {
        val bookIndex = books.indexOfFirst { it.id == bookId }
        if (bookIndex == -1 || books[bookIndex].isBorrowed) return false

        books[bookIndex] = books[bookIndex].copy(isBorrowed = true)
        borrowRecords.add(
            BorrowRecord(studentId, bookId, LocalDate.now().toString())
        )
        return true
    }

    fun returnBook(bookId: Int): Boolean {
        val bookIndex = books.indexOfFirst { it.id == bookId }
        if (bookIndex == -1 || !books[bookIndex].isBorrowed) return false

        books[bookIndex] = books[bookIndex].copy(isBorrowed = false)
        borrowRecords.removeAll { it.bookId == bookId }
        return true
    }

    fun getBorrowedBooksByStudent(studentId: Int): List<Book> {
        val borrowedIds = borrowRecords
            .filter { it.studentId == studentId }
            .map { it.bookId }
            .toSet()
        return books.filter { it.id in borrowedIds }
    }
}
