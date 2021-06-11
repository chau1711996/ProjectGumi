package com.example.projectgumi.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.example.projectgumi.MainActivity
import com.example.projectgumi.R
import com.example.projectgumi.databinding.ActivityOnBordingBinding
import com.example.projectgumi.ui.account.ProfileActivity


class OnBordingActivity : AppCompatActivity() {
    private var binding: ActivityOnBordingBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnBordingBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        binding?.buttonStart?.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            finish()
        }
    }
}