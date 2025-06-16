package com.example.lifecounter.ui

import androidx.lifecycle.ViewModel
import com.example.lifecounter.data.Player
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/** Stores and manages the data for the GameUiState**/
class GameViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(GameUiState())
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()

    /** Initalizes uiState. Meant to be called once per game from the StartScreen
     * @param playerCount the amount of players in the game
     * @param lifeTotal the starting life total of each player
     * @return null
     */
    fun initializeUiState(playerCount: Int, lifeTotal: Int){
        val playerList: List<Player> = List(playerCount
        ) {
            val p = Player(
                lifeTotal, "Player " + (it + 1), false,
                MutableList(playerCount){0}
            )
            for(i in 0 until playerCount){
                p.commanderDamage[i] = 0
            }
            p
        }

        _uiState.value = GameUiState(
            playerCount = playerCount,
            startingLife = lifeTotal,
            players = playerList,
            gameOver = false
        )


    }
}