package com.gumi.projectgumi.ui.checkout

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.gumi.projectgumi.R
import com.gumi.projectgumi.data.model.OrdersModel
import com.gumi.projectgumi.databinding.FragmentCheckoutBinding
import com.gumi.projectgumi.ui.order.OrderAcceptedFragment
import com.gumi.projectgumi.ui.order.OrderFailedFragment
import com.gumi.projectgumi.utils.Utils
import com.gumi.projectgumi.viewmodel.CheckoutViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val MONEY = "money"
private const val COUNT = "count"

class CheckoutFragment : DialogFragment() {
    private var money: String? = null
    private var count: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            money = it.getString(MONEY)
            count = it.getString(COUNT)
        }
    }

    private lateinit var binding: FragmentCheckoutBinding
    private val viewModel by viewModel<CheckoutViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dialog?.window?.setGravity(Gravity.CENTER_HORIZONTAL or Gravity.BOTTOM)
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
        var cost = ""
        binding.apply {
            money?.let {
                cost = it
                if (cost.length > 6) {
                    cost = cost.substring(0, 6)
                }
                textTotalCoast.text = cost
            }
            imageClose.setOnClickListener {
                dismiss()
            }
            btnCheckout.setOnClickListener {
                count?.let { count ->
                    Firebase.auth.currentUser?.let { user ->
                        viewModel.insertOrders(
                            OrdersModel(
                                0,
                                "Shipper",
                                "Discard",
                                count,
                                cost,
                                user.uid
                            )
                        )
                    }
                }
            }
        }

        viewModel.status.observe(requireActivity())
        {
            it?.let {
                if (it.status.equals("success")) {
                    dismiss()
                    Utils.showDialogFragment(
                        activity,
                        OrderAcceptedFragment(),
                        OrderAcceptedFragment.TAG
                    )
                    viewModel.deleteAllCart()
                } else {
                    dismiss()
                    Utils.showDialogFragment(
                        activity,
                        OrderFailedFragment(),
                        OrderAcceptedFragment.TAG
                    )
                }
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

        @JvmStatic
        fun newInstance(money: String, count: String) =
            CheckoutFragment().apply {
                arguments = Bundle().apply {
                    putString(MONEY, money)
                    putString(COUNT, count)
                }
            }
    }

}