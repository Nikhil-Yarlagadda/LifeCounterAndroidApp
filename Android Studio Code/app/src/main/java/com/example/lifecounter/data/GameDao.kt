package com.example.lifecounter.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.lifecounter.ui.GameUiState
import kotlinx.coroutines.flow.Flow

@Dao
interface GameDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(plyr: Player)

    @Update
    suspend fun update(plyr: Player)

    @Delete
    suspend fun delete(plyr: Player)

    @Query("DELETE from plyrs")
    suspend fun clearPlayers()

    @Query("SELECT * from plyrs WHERE id = :id")
    fun getPlyr(id: Int): Flow<Player>

    @Query("SELECT * from plyrs ORDER BY id")
    fun getAllplyr(): Flow<List<Player>>


    @Query("SELECT * FROM game_state WHERE id = 1")
    suspend fun getGameState(): GameStateEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setGameState(state: GameStateEntity)

    @Query("SELECT * FROM game_state WHERE id = 1")
    fun getGameStateFlow(): Flow<GameStateEntity?>
}