package com.example.projectgumi.ui.checkout

import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.projectgumi.R
import com.example.projectgumi.databinding.FragmentCheckoutBinding
import com.example.projectgumi.ui.order.OrderAcceptedFragment
import com.example.projectgumi.utils.Utils

class CheckoutFragment : DialogFragment() {


    private lateinit var binding: FragmentCheckoutBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        dialog?.window?.setGravity(Gravity.CENTER_HORIZONTAL or Gravity.BOTTOM)
        //make dialog_picker transparent
        //dialog_picker?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        dialog?.window?.setBackgroundDrawableResource(R.color.transparent)

        binding = FragmentCheckoutBinding.bind(
            inflater.inflate(
                R.layout.fragment_checkout,
                container,
                false
            )
        )
        binding.apply {
            lifecycleOwner = this@CheckoutFragment
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            imageClose.setOnClickListener {
                dismiss()
            }
            btnCheckout.setOnClickListener {
                dismiss()
                Utils.showDialogFragment(activity, OrderAcceptedFragment(), OrderAcceptedFragment.TAG)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    companion object {
        const val TAG = "CheckoutFragment"
    }

}