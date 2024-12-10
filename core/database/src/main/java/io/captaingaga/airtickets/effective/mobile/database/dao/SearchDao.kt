package io.captaingaga.airtickets.effective.mobile.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.captaingaga.airtickets.effective.mobile.database.entity.SearchEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SearchDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: SearchEntity)

    @Query("SELECT * From search ORDER by id DESC LIMIT 1")
    fun findLast(): Flow<SearchEntity?>
}