package com.example.lifecounter.data
/** This class represents the data of a player in the game**/
data class Player(
    /** Life points of the player **/
    var life: Int = 0,

    /** Name of the player **/
    var name: String,

    /** Stores whether or not the player has won the game **/
    var hasWon: Boolean,

    /** The amount of commander damage this player has dealt to the other players **/
    var commanderDamage: MutableList<Int>,

    /** Amount of poison counters the player has **/
    var poisonCounters: Int = 0,

    /** Amount of energy counters the player has **/
    var energyCounters: Int = 0,
    /** TODO: add more counters if it is needed during gameplay **/
    var countersList: MutableList<Counter> = emptyList<Counter>().toMutableList()
)

class Counter(
    var name: String,
    var value: Int
)
