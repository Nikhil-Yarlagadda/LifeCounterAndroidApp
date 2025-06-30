package com.example.lifecounter

import android.app.Application
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
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
import com.example.lifecounter.ui.GameViewModelFactory


enum class LifeCounterScreen {
    Start,
    Game,
    Settings
}

@Composable
fun LifeCounter(
    viewModel: GameViewModel = viewModel(
        factory = GameViewModelFactory(LocalContext.current.applicationContext as Application)
    ),
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val gameUiState = viewModel.uiState.collectAsState().value
    val initialScreen = if(gameUiState.gameOver) LifeCounterScreen.Start.name else LifeCounterScreen.Game.name


    NavHost(
        navController = navController,
        startDestination = initialScreen,
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
                players = gameUiState.players,
                onSettingsClicked = {navController.navigate(LifeCounterScreen.Settings.name)},
                onLifeChange = {player: Player, number: Int -> player.life += number}
            )
        }
    }
}