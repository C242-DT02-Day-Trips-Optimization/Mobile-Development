package com.bizzagi.daytripoptimization.team2.data.response

import java.io.Serializable

data class ClusteringResponse(
    val grouped_clusters: List<GroupedCluster>,
    val final_unvisitable: List<String>,
    val recommended_days: Int
) : Serializable

data class GroupedCluster(
    val cluster: Int,
    val schedule: List<ScheduleItem>,
    val avg_duration: Int
) : Serializable

data class ScheduleItem(
    val name: String,
    val avg_duration: Int,
    val travel_time: Int?, // Nullable if no data
    val mode: String?, // Nullable if no data
    val coordinates: List<Double>? = null
) : Serializable