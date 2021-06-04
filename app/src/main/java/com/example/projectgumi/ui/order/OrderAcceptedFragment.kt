package com.example.projectgumi.ui.order

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.gumiproject8.utils.hide
import com.example.projectgumi.MainActivity
import com.example.projectgumi.R
import com.example.projectgumi.databinding.FragmentOrderAcceptedBinding
import com.example.projectgumi.ui.shop.ShopFragment
import com.example.projectgumi.utils.Utils
import com.google.android.gms.common.util.DataUtils

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class OrderAcceptedFragment : DialogFragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

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
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            OrderAcceptedFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}