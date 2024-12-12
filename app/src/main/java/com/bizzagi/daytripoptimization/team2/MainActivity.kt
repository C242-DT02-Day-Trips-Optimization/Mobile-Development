package com.bizzagi.daytripoptimization.team2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.bizzagi.daytripoptimization.team2.databinding.ActivityMainBinding
import com.bizzagi.daytripoptimization.team2.ui.history.HistoryFragment
import com.bizzagi.daytripoptimization.team2.ui.home.HomeFragment
import com.bizzagi.daytripoptimization.team2.ui.profile.ProfileFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, HomeFragment())
                .commit()
        }

        binding.bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    loadFragment(HomeFragment())
                    true
                }
                R.id.nav_history -> {
                    loadFragment(HistoryFragment())

                    true
                }
                R.id.nav_profile -> {
                    loadFragment(ProfileFragment())

                    true
                }
                else -> false
            }
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }
}