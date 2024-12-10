package com.bizzagi.daytripoptimization.team2.data.request

data class ClusteringRequest(
    val points: List<Point>,
    val num_clusters: Int,
    val province: String,
    val daily_start_time: String,
    val daily_end_time: String
)

data class Point(
    val name: String,
    val coordinates: List<Double>
)