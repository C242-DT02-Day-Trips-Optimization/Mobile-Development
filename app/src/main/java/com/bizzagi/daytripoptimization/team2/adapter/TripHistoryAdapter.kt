package com.bizzagi.daytripoptimization.team2.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bizzagi.daytripoptimization.team2.database.TripHistoryEntity
import com.bizzagi.daytripoptimization.team2.databinding.ItemTripHistoryBinding

class TripHistoryAdapter(
    private val tripHistories: List<TripHistoryEntity>,
    private val onItemClick: (TripHistoryEntity) -> Unit
) : RecyclerView.Adapter<TripHistoryAdapter.TripHistoryViewHolder>() {

    inner class TripHistoryViewHolder(private val binding: ItemTripHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: TripHistoryEntity) {
            binding.historyDestination.text = item.province
            binding.historyTotalDays.text = "${item.totalDays} Day"
            binding.historyTotalDestination.text = "${item.totalDestination} Destination"

            // Event onclick
            binding.root.setOnClickListener {
                onItemClick(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TripHistoryViewHolder {
        val binding = ItemTripHistoryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return TripHistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TripHistoryViewHolder, position: Int) {
        holder.bind(tripHistories[position])
    }

    override fun getItemCount(): Int = tripHistories.size
}
