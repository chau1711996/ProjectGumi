package com.example.projectgumi.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import com.example.gumiproject8.utils.hide
import com.example.gumiproject8.utils.show
import com.example.projectgumi.MainActivity
import com.example.projectgumi.R
import com.example.projectgumi.databinding.ActivityOnBordingBinding
import com.example.projectgumi.ui.account.ProfileActivity
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback


class OnBordingActivity : AppCompatActivity() {

    private var binding: ActivityOnBordingBinding? = null
    private var mInterstitialAd: InterstitialAd? = null
    private final var TAG = "LogOnBordingActivity"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnBordingBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        init()
        binding?.buttonStart?.setOnClickListener {
            mInterstitialAd?.let {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                finish()
                it.show(this)
            }
        }
    }

    private fun init(){
        binding?.buttonStart?.hide()
        MobileAds.initialize(this) {}
        setupInterstitialAd()
        mInterstitialAd?.fullScreenContentCallback = object: FullScreenContentCallback() {
            override fun onAdDismissedFullScreenContent() {
                Log.d(TAG, "Ad was dismissed.")
            }

            override fun onAdFailedToShowFullScreenContent(adError: AdError?) {
                Log.d(TAG, "Ad failed to show.")
            }

            override fun onAdShowedFullScreenContent() {
                Log.d(TAG, "Ad showed fullscreen content.")
                mInterstitialAd = null;
            }
        }
    }

    private fun setupInterstitialAd() {
        val adRequest = AdRequest.Builder().build()
        InterstitialAd.load(
            this,
            "ca-app-pub-3940256099942544/1033173712",
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    Log.d(TAG, adError.message)
                    mInterstitialAd = null
                }

                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    Log.d(TAG, "Ad was loaded.")
                    mInterstitialAd = interstitialAd
                    binding?.buttonStart?.show()
                }
            })
    }
}