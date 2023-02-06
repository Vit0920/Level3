package com.vkunitsyn.level3.ui

import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.vkunitsyn.level3.R
import com.vkunitsyn.level3.databinding.ActivityMainBinding
import com.vkunitsyn.level3.utils.Constants
import com.vkunitsyn.level3.utils.FeatureFlags

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var prefs: SharedPreferences
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        prefs = applicationContext.getSharedPreferences(Constants.SHARED_PREFS_NAME, MODE_PRIVATE)

        if(FeatureFlags.transactionsEnabled){
            if (savedInstanceState == null) {
                supportFragmentManager.commit {
                    add<AuthFragment>(R.id.fragment_container)
                    addToBackStack(null)
                }
            }
            else{
                navController = Navigation.findNavController(this, R.id.fragment_container)
            }
        }

    }
}