package surivz.productivity.accounting

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import surivz.productivity.accounting.screens.home.HomeScreen

sealed class AppScreens(val route: String) {
    data object Home : AppScreens("home")
    data object AddMovement : AppScreens("add_movement/{type}") {
        fun createRoute(type: String) = "add_movement/$type"
    }

    data object Settings : AppScreens("settings")

    data object EditMovement : AppScreens("edit_movement/{movementId}") {
        fun createRoute(movementId: Long) = "edit_movement/$movementId"
    }

    data object MovementDetail : AppScreens("movement_detail/{movementId}") {
        fun createRoute(movementId: Long) = "movement_detail/$movementId"
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = AppScreens.Home.route
    ) {
        composable(route = AppScreens.Home.route) {
            HomeScreen(
                onNavigateToAdd = { navController.navigate(AppScreens.AddMovement.route) },
                onNavigateToSettings = { navController.navigate(AppScreens.Settings.route) },
                onMovementClick = { id ->
                    navController.navigate(
                        AppScreens.EditMovement.createRoute(
                            id
                        )
                    )
                }
            )
        }

        composable(
            route = AppScreens.AddMovement.route,
            arguments = listOf(navArgument("type") { type = NavType.StringType })
        ) { backStackEntry ->
            backStackEntry.arguments?.getString("type") ?: "INCOME"
        }

        composable(
            route = AppScreens.EditMovement.route,
            arguments = listOf(navArgument("movementId") { type = NavType.LongType })
        ) { backStackEntry ->
            val movementId = backStackEntry.arguments?.getLong("movementId") ?: 0L
            androidx.compose.material3.Text(text = "Editando Movimiento ID: $movementId")
        }

        composable(
            route = AppScreens.MovementDetail.route,
            arguments = listOf(navArgument("movementId") { type = NavType.LongType })
        ) { backStackEntry ->
            val movementId = backStackEntry.arguments?.getLong("movementId") ?: 0L
            androidx.compose.material3.Text(text = "Detalle Movimiento ID: $movementId")
        }

        composable(route = AppScreens.Settings.route) {
            androidx.compose.material3.Text(text = "Pantalla Configuración")
        }
    }
}