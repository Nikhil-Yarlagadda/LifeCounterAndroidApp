package com.example.lifecounter.data

import androidx.room.Entity
import androidx.room.PrimaryKey

/** This class represents the data of a player in the game**/
@Entity(tableName = "plyrs")
data class Player(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    /** Life points of the player **/
    var life: Int = 0,

    /** Name of the player **/
    var name: String,

    /** Stores whether or not the player has won the game **/
    var hasWon: Boolean,

    /** The amount of commander damage this player has dealt to the other players **/
    var commanderDamage: List<Int>,

    /** Amount of poison counters the player has **/
    var poisonCounters: Int = 0,

    /** Amount of energy counters the player has **/
    var energyCounters: Int = 0,
    /** TODO: add more counters if it is needed during gameplay **/
    var countersList: List<Counter> = emptyList()
)

data class Counter(
    val name: String,
    var value: Int,
)
