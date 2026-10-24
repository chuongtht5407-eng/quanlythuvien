package com.example.librarymanagement.data.model

data class Book(
    val id: Int,
    val title: String,
    val author: String,
    val isBorrowed: Boolean = false
)
