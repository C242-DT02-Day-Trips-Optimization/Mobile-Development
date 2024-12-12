package com.bizzagi.daytripoptimization.team2.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bizzagi.daytripoptimization.team2.R
import com.bizzagi.daytripoptimization.team2.data.response.GroupedCluster
import com.bizzagi.daytripoptimization.team2.data.response.ScheduleItem
import com.bizzagi.daytripoptimization.team2.databinding.ItemDestinationDetailBinding
import com.bizzagi.daytripoptimization.team2.databinding.ItemResultTripBinding

class ClusterAdapter(private val groupedClusters: List<GroupedCluster>) :
    RecyclerView.Adapter<ClusterAdapter.ClusterViewHolder>() {

    private var dayCounter = 1 // Counter untuk menghitung hari secara manual

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClusterViewHolder {
        val binding = ItemResultTripBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ClusterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ClusterViewHolder, position: Int) {
        val groupedCluster = groupedClusters[position]
        holder.bind(groupedCluster, dayCounter)
        dayCounter++ // Increment counter setelah setiap bind
    }

    override fun getItemCount(): Int {
        return groupedClusters.size
    }

    inner class ClusterViewHolder(private val binding: ItemResultTripBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(groupedCluster: GroupedCluster, day: Int) {
            // Mendapatkan data dari groupedCluster
            val schedule = groupedCluster.schedule

            // Membuat adapter untuk RecyclerView schedule
            val scheduleAdapter = ScheduleAdapter(schedule)

            // Mengatur layout manager untuk RecyclerView schedule
            binding.viewDestination.layoutManager = LinearLayoutManager(binding.root.context)

            // Mengatur adapter untuk RecyclerView schedule
            binding.viewDestination.adapter = scheduleAdapter

            // Menampilkan data ke layout
            binding.resultDay.text = "Day $day"
        }
    }
}

class ScheduleAdapter(private val schedule: List<ScheduleItem>) :
    RecyclerView.Adapter<ScheduleAdapter.ScheduleViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleViewHolder {
        val binding = ItemDestinationDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ScheduleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ScheduleViewHolder, position: Int) {
        val scheduleItem = schedule[position]
        holder.bind(scheduleItem)
    }

    override fun getItemCount(): Int {
        return schedule.size
    }

    inner class ScheduleViewHolder(private val binding: ItemDestinationDetailBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(scheduleItem: ScheduleItem) {
            // Menampilkan data ke layout
            binding.resultDestination.text = scheduleItem.name
            if (scheduleItem.travel_time != null) {
                if (scheduleItem.mode == "walking") {
                    binding.resultMode.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_walking, 0, 0, 0)
                }

                if (scheduleItem.mode == "driving") {
                    binding.resultMode.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_driving, 0, 0, 0)
                }

                binding.resultMode.text = "${scheduleItem.travel_time} minutes of ${scheduleItem.mode}"
            } else {
                binding.resultMode.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_start, 0, 0, 0)

                binding.resultMode.text = "Start destination"
            }
        }
    }
}
