package com.example.lifecounter.ui

import com.example.lifecounter.data.Player

/** Class represents information about the state of the game**/
data class GameUiState(
    /** Amount of players in the game **/
    val playerCount: Int = 0,

    /** The life each player starts at **/
    val startingLife: Int = 0,

    /** List of all Player objects in the game **/
    val players: List<Player> = emptyList(),

    /** Whether or not the game is over AKA whether or not a player has won **/
    var gameOver: Boolean = false
)
