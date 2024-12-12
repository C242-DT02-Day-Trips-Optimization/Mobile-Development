package com.bizzagi.daytripoptimization.team2.ui.history

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bizzagi.daytripoptimization.team2.adapter.TripHistoryAdapter
import com.bizzagi.daytripoptimization.team2.database.TripResultDatabase
import com.bizzagi.daytripoptimization.team2.databinding.FragmentHistoryBinding
import kotlinx.coroutines.launch

class HistoryFragment : Fragment() {
    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: TripHistoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize RecyclerView
        binding.viewHistory.layoutManager = LinearLayoutManager(requireContext())

        // Show ProgressBar before data retrieval
        binding.progressBar.visibility = View.VISIBLE
        binding.viewHistory.visibility = View.GONE
        binding.labelNoData.visibility = View.GONE

        lifecycleScope.launch {
            try {
                val db = TripResultDatabase.getDatabase(requireContext())
                val tripHistories = db.tripHistoryDao().getAllTripHistory()

                if (tripHistories.isEmpty()) {
                    // Show "No Data" view
                    binding.labelNoData.visibility = View.VISIBLE
                    binding.viewHistory.visibility = View.GONE
                } else {
                    // Hide "No Data" view and show RecyclerView
                    binding.labelNoData.visibility = View.GONE
                    binding.viewHistory.visibility = View.VISIBLE

                    // Set adapter
                    adapter = TripHistoryAdapter(tripHistories) { tripHistory ->
                        // Navigate to HistoryDetailActivity with data
                        val intent = Intent(requireContext(), HistoryDetailActivity::class.java).apply {
                            putExtra("ID", tripHistory.id)
                            putExtra("CLUSTER_DATA", tripHistory.clusterData)
                            putExtra("CLUSTER_DESTINATION", tripHistory.clusterDestination)
                        }
                        startActivity(intent)
                    }
                    binding.viewHistory.adapter = adapter
                }
            } catch (e: Exception) {
                // Handle any errors
                binding.labelNoData.visibility = View.VISIBLE
                binding.viewHistory.visibility = View.GONE
                Log.e("HistoryFragment", "Error retrieving data: ${e.message}", e)
            } finally {
                // Hide ProgressBar after data is retrieved
                binding.progressBar.visibility = View.GONE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }
}