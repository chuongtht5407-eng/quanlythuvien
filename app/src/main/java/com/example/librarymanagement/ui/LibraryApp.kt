package com.example.librarymanagement.ui

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.librarymanagement.ui.navigation.LibraryRoutes
import com.example.librarymanagement.ui.screens.BookListScreen
import com.example.librarymanagement.ui.screens.ManagementScreen
import com.example.librarymanagement.ui.screens.StudentListScreen
import com.example.librarymanagement.viewmodel.LibraryViewModel

@Composable
fun LibraryApp() {
    val navController = rememberNavController()
    val viewModel: LibraryViewModel = viewModel()
    val backStack by navController.currentBackStackEntryAsState()
    val currentRoute = backStack?.destination?.route ?: LibraryRoutes.MANAGEMENT

    Scaffold(
        bottomBar = {
            LibraryBottomBar(
                currentRoute = currentRoute,
                onNavigate = { route ->
                    navController.navigate(route) {
                        popUpTo(LibraryRoutes.MANAGEMENT) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = remember { SnackbarHostState() })
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = LibraryRoutes.MANAGEMENT,
            modifier = Modifier
        ) {
            composable(LibraryRoutes.MANAGEMENT) {
                ManagementScreen(viewModel = viewModel, contentPadding = padding)
            }
            composable(LibraryRoutes.BOOKS) {
                BookListScreen(viewModel = viewModel, contentPadding = padding)
            }
            composable(LibraryRoutes.STUDENTS) {
                StudentListScreen(viewModel = viewModel, contentPadding = padding)
            }
        }
    }
}

@Composable
private fun LibraryBottomBar(
    currentRoute: String,
    onNavigate: (String) -> Unit
) {
    NavigationBar {
        NavigationBarItem(
            selected = currentRoute == LibraryRoutes.MANAGEMENT,
            onClick = { onNavigate(LibraryRoutes.MANAGEMENT) },
            icon = { Text("⌂") },
            label = { Text("Quản lý") }
        )
        NavigationBarItem(
            selected = currentRoute == LibraryRoutes.BOOKS,
            onClick = { onNavigate(LibraryRoutes.BOOKS) },
            icon = { Text("▤") },
            label = { Text("DS Sách") }
        )
        NavigationBarItem(
            selected = currentRoute == LibraryRoutes.STUDENTS,
            onClick = { onNavigate(LibraryRoutes.STUDENTS) },
            icon = { Text("♙") },
            label = { Text("Sinh viên") }
        )
    }
}
