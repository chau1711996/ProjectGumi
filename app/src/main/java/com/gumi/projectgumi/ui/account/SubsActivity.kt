package com.gumi.projectgumi.ui.account

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.BillingFlowParams
import com.android.billingclient.api.SkuDetails
import com.gumi.gumiproject8.utils.goToActivity
import com.gumi.gumiproject8.utils.setVisible
import com.gumi.projectgumi.MainActivity
import com.gumi.projectgumi.adapter.SubsAdapter
import com.gumi.projectgumi.databinding.ActivitySubsBinding
import com.gumi.projectgumi.utils.BillingSubcribe

class SubsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySubsBinding
    private lateinit var subsAdapter: SubsAdapter
    private lateinit var billingClient: BillingClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySubsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val billing = BillingSubcribe.getInstance(TAG, this)
        billingClient = billing.billingClient!!
        subsAdapter = SubsAdapter { clickBuyItem(it) }

        binding.apply {
            val linearLayoutManager = LinearLayoutManager(binding.root.context)
            rcvItem.apply {
                setHasFixedSize(true)
                layoutManager = linearLayoutManager
                adapter = subsAdapter
            }
            billing.isSubscribe.observe(this@SubsActivity){
                it?.let {
                    txtShowSub.setVisible(it)
                    rcvItem.setVisible(!it)
                }
            }
            imageLeft.setOnClickListener {
                goToActivity(MainActivity::class.java)
            }
        }

        billing.listSubs.observe(this){
            subsAdapter.submitList(it)
        }

    }

    private fun clickBuyItem(skuDetails: SkuDetails) {
        val purchaseParams = BillingFlowParams.newBuilder()
            .setSkuDetails(skuDetails)
            .build()
        billingClient.launchBillingFlow(this, purchaseParams)
    }

    companion object{
        const val TAG = "LogSubsActivity"
    }
}