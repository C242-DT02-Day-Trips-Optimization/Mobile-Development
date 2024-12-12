package com.bizzagi.daytripoptimization.team2.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TripHistoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTripHistory(tripHistory: TripHistoryEntity)

    @Query("SELECT * FROM trip_history")
    suspend fun getAllTripHistory(): List<TripHistoryEntity>

    @Query("SELECT * FROM trip_history WHERE id = :id")
    suspend fun getTripHistoryById(id: String): TripHistoryEntity?

    @Delete
    suspend fun deleteTripHistory(tripHistory: TripHistoryEntity)
}