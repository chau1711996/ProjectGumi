package com.gumi.projectgumi.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.gumi.projectgumi.R
import kotlinx.coroutines.*


class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        GlobalScope.launch(Dispatchers.IO) {
            delay(2000)
            val intent = Intent(this@SplashActivity, OnBordingActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}