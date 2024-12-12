package com.bizzagi.daytripoptimization.team2.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "trip_history")
data class TripHistoryEntity(
    @PrimaryKey val id: String, // Random unique ID
    val province: String,
    val totalDays: Int,
    val totalDestination: Int,
    val clusterData: String, // Serialized JSON string
    val clusterDestination: String // Serialized JSON string
)
