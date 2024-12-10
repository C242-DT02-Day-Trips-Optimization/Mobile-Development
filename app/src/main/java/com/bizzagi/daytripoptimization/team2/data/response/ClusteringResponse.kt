package com.bizzagi.daytripoptimization.team2.data.response

data class ClusteringResponse(
    val grouped_clusters: List<GroupedCluster>,
    val final_unvisitable: List<String>, // Jika ada data yang tidak dapat dikunjungi, bisa disesuaikan dengan tipe data yang tepat
    val recommended_days: Int
)

data class GroupedCluster(
    val cluster: Int,
    val schedule: List<ScheduleItem>,
    val avg_duration: Int
)

data class ScheduleItem(
    val name: String,
    val avg_duration: Int,
    val travel_time: Int?, // Nullable jika tidak ada data
    val mode: String? // Nullable jika tidak ada data
)