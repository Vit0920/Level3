package com.vkunitsyn.level3.ui

import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.vkunitsyn.level3.R
import com.vkunitsyn.level3.databinding.ActivityAuthBinding
import com.vkunitsyn.level3.utils.Constants

class AuthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthBinding
    private lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)
        prefs = applicationContext.getSharedPreferences(Constants.SHARED_PREFS_NAME, MODE_PRIVATE)
        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                add<AuthFragment>(R.id.fragment_container)
            }
        }
    }
}