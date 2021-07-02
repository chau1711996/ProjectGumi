package com.gumi.projectgumi.ui.order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.gumi.projectgumi.MainActivity
import com.gumi.projectgumi.R
import com.gumi.projectgumi.databinding.FragmentOrderAcceptedBinding
import com.gumi.projectgumi.utils.Utils

class OrderAcceptedFragment : DialogFragment() {

    private lateinit var binding: FragmentOrderAcceptedBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.window?.setBackgroundDrawableResource(R.color.transparent)
        binding = FragmentOrderAcceptedBinding.bind(inflater.inflate(R.layout.fragment_order_accepted, container, false))
        binding.apply {
            lifecycleOwner = this@OrderAcceptedFragment
        }
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            layoutHead.imageLeft.hide()
            btnBackHome.setOnClickListener {
                //Utils.showReplaceFragment(activity, ShopFragment())
                if(context is MainActivity) {
                    (context as MainActivity).goToFragment(MainActivity.SHOP)
                }
                dismiss()
            }
            btnTrackOrder.setOnClickListener {
                Utils.showDialogFragment(activity, OrdersFragment(), OrdersFragment.TAG)
                dismiss()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
    }

    companion object {
        const val TAG = "OrderAcceptedFragment"
    }
}