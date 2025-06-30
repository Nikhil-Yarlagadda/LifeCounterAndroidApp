package com.example.lifecounter.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lifecounter.data.GameDatabase
import com.example.lifecounter.data.GameStateEntity
import com.example.lifecounter.data.Player
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

/** Stores and manages the data for the GameUiState**/
class GameViewModel(application: Application) : AndroidViewModel(application) {

    private val _uiState = MutableStateFlow(GameUiState())
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()

    private val db = GameDatabase.getDatabase(application)
    private val dao = db.gameDao()

    init{
        viewModelScope.launch{
            launch{
                dao.getGameStateFlow().collect{ state ->
                    val gameOver = state?.gameOver ?: true

                    if(!gameOver){
                        val players = dao.getAllplyr().first()
                        _uiState.value = GameUiState(
                            players = players,
                            playerCount = players.size,
                            gameOver = false
                        )
                    }
                    else{
                        _uiState.value = GameUiState(
                            players = emptyList(),
                            playerCount = 0,
                            gameOver = true
                        )
                    }
                }
            }

        }
    }

    /** Initalizes uiState. Meant to be called once per game from the StartScreen
     * @param playerCount the amount of players in the game
     * @param lifeTotal the starting life total of each player
     * @return null
     */
    fun initializeUiState(playerCount: Int, lifeTotal: Int){

        viewModelScope.launch {

            dao.clearPlayers()

            val playerList: List<Player> = List(playerCount
            ) {
                val p = Player(
                    life = lifeTotal,
                    name = "Player " + (it + 1),
                    hasWon = false,
                    commanderDamage = MutableList(playerCount){0}
                )
//                for(i in 0 until playerCount){
//                    p.commanderDamage[i] = 0
//                }
                dao.insert(p)
                p
            }

            _uiState.value = GameUiState(
                playerCount = playerCount,
                startingLife = lifeTotal,
                players = playerList,
                gameOver = false
            )
            dao.setGameState(GameStateEntity(gameOver = false))
        }
    }

    fun updatePlayer(player: Player){
        viewModelScope.launch{
            dao.update(player)
        }
    }
}