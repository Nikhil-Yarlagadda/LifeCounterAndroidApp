package com.example.lifecounter

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.lifecounter.ui.GameViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.lifecounter.ui.GameScreen
import com.example.lifecounter.ui.StartScreen
import com.example.lifecounter.data.Player


enum class LifeCounterScreen {
    Start,
    Game,
    Settings
}

@Composable
fun LifeCounter(
    viewModel: GameViewModel = GameViewModel(),
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val gameUiState = viewModel.uiState.collectAsState().value



    NavHost(
        navController = navController,
        startDestination = LifeCounterScreen.Start.name,
        modifier = Modifier
    ) {
        composable(route = LifeCounterScreen.Start.name) {
            val startScreen = StartScreen()
            startScreen.startScreen(onStartPressed = { playerAmount: Int, lifeTotal: Int ->
                viewModel.initializeUiState(playerAmount, lifeTotal)
                navController.navigate(LifeCounterScreen.Game.name)
            })
        }
        composable(route = LifeCounterScreen.Game.name) {
            val gameScreen = GameScreen()
            gameScreen.GameScreen(
                gameUiState.players,
                {navController.navigate(LifeCounterScreen.Settings.name)},
                {player: Player, number: Int -> player.life += number}
            )
        }
    }
}