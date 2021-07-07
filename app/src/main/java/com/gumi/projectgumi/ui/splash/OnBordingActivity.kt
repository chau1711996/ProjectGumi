package com.gumi.projectgumi.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.gumi.projectgumi.MainActivity
import com.gumi.projectgumi.R
import com.gumi.projectgumi.databinding.ActivityOnBordingBinding
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.gumi.gumiproject8.utils.goToActivity
import com.gumi.gumiproject8.utils.hide
import com.gumi.gumiproject8.utils.setVisible
import com.gumi.gumiproject8.utils.show
import com.gumi.projectgumi.utils.BillingSubcribe


class OnBordingActivity : AppCompatActivity() {

    private var binding: ActivityOnBordingBinding? = null
    private var mInterstitialAd: InterstitialAd? = null
    private final var TAG = "LogOnBordingActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnBordingBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        binding?.buttonStart?.hide()
        BillingSubcribe.getInstance(TAG, this).isSubscribe.observe(this) {
            it?.let {
                if (!it) {
                    init()
                }else{
                    binding?.buttonStart?.show()
                }
            }
        }

        binding?.buttonStart?.setOnClickListener {
            goToActivity(MainActivity::class.java)
            mInterstitialAd?.show(this)
        }
    }

    private fun init() {
        MobileAds.initialize(this) {}
        setupInterstitialAd()
        mInterstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
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