package com.gumi.projectgumi.utils

import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import com.android.billingclient.api.*

class BillingSubcribe(private val tag: String, context: Context) : PurchasesUpdatedListener, LifecycleObserver {
    var billingClient: BillingClient? = null
    private var acknowledgePurchaseResponseListener: AcknowledgePurchaseResponseListener? = null
    var isSubscribe = MutableLiveData<Boolean?>()
    var listSubs = MutableLiveData<MutableList<SkuDetails>?>()

    init {
        acknowledgePurchaseResponseListener = AcknowledgePurchaseResponseListener {
            Log.d(tag, "AcknowledgePurchaseResponseListener")
            if (it.responseCode == BillingClient.BillingResponseCode.OK) {
                Log.d(tag, "isAcknowledged")
                isSubscribe.postValue(true)
            }
        }
        if (billingClient == null) {
            billingClient = BillingClient.newBuilder(context)
                .setListener(this)
                .enablePendingPurchases()
                .build()
        }
        val purchaseResponseListener = PurchasesResponseListener{ result, list ->
            if(result.responseCode == BillingClient.BillingResponseCode.OK){
                if (list.size > 0) {
                    handlePurchase(list[0])
                } else {
                    querySkuDetails()
                }
            }
        }

        billingClient!!.startConnection(object : BillingClientStateListener {
            override fun onBillingSetupFinished(billingResult: BillingResult) {
                if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                    billingClient!!.queryPurchasesAsync(BillingClient.SkuType.SUBS,purchaseResponseListener)
                }
            }

            override fun onBillingServiceDisconnected() {
                // Try to restart the connection on the next request to
                // Google Play by calling the startConnection() method.
                Log.d(tag, "onBillingServiceDisconnected")
            }
        })
    }

    private fun handlePurchase(purchase: Purchase) {
        Log.d(tag, "handlePurchase")
        if (purchase.purchaseState == Purchase.PurchaseState.PURCHASED) {
            Log.d(tag, "State PURCHASED")
            if (!purchase.isAcknowledged) {
                Log.d(tag, "NOT isAcknowledged")
                val acknowledgePurchaseParams = AcknowledgePurchaseParams.newBuilder()
                    .setPurchaseToken(purchase.purchaseToken)
                    .build()
                billingClient?.acknowledgePurchase(
                    acknowledgePurchaseParams,
                    acknowledgePurchaseResponseListener!!
                )
            } else {
                Log.d(tag, "isAcknowledged")
                isSubscribe.postValue(true)
            }
        } else {
            Log.d(tag, "state NOT PURCHASED")
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun disconect(){
        billingClient?.endConnection()
    }

    fun querySkuDetails() {
        isSubscribe.postValue(false)
        Log.d(tag, "querySkuDetails")
        val skuList = ArrayList<String>()
        skuList.add("vip_2021")
        val params = SkuDetailsParams.newBuilder()
            .setSkusList(skuList).setType(BillingClient.SkuType.SUBS)
            .build()
        billingClient?.querySkuDetailsAsync(params) { billingResult, list ->
            Log.d(tag, "querySkuDetails list ${list.toString()}")
            if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                listSubs.postValue(list)
            }
        }
    }

    override fun onPurchasesUpdated(
        billingResult: BillingResult,
        purchases: MutableList<Purchase>?
    ) {
        if (billingResult.responseCode == BillingClient.BillingResponseCode.OK && purchases != null) {
            Log.d(tag, "onPurchasesUpdated list: ${purchases.size}")
            handlePurchase(purchases[0])
        } else if (billingResult.responseCode == BillingClient.BillingResponseCode.USER_CANCELED) {
            Log.d(tag, "USER_CANCELED")
        }
    }

    companion object{
        @Volatile
        private var sInstance: BillingSubcribe? = null
        @JvmStatic
        fun getInstance(
            tag: String,
            context: Context
        ) = sInstance ?: synchronized(this) {
            BillingSubcribe(tag, context).apply {
                sInstance = this
            }
        }
    }
}