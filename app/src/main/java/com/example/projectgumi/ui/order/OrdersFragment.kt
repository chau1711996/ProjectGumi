package com.example.projectgumi.ui.order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projectgumi.R
import com.example.projectgumi.adapter.OrdersAdapter
import com.example.projectgumi.databinding.FragmentOrdersBinding
import com.example.projectgumi.viewmodel.OrdersViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class OrdersFragment : DialogFragment() {
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }
    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
    }
    private lateinit var binding: FragmentOrdersBinding
    private lateinit var ordersAdapter: OrdersAdapter
    private val viewModel by viewModel<OrdersViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dialog?.window?.setBackgroundDrawableResource(R.color.transparent)
        binding = FragmentOrdersBinding.bind(inflater.inflate(R.layout.fragment_orders, container, false))
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ordersAdapter = OrdersAdapter()
        binding.apply {
            imageLeft.setOnClickListener {
                dismiss()
            }
            adapterOrders.adapter = ordersAdapter
            adapterOrders.layoutManager = LinearLayoutManager(requireContext())
        }
        viewModel.getOrders()
        viewModel.dataOrders.observe(requireActivity()){
            ordersAdapter.submitList(it)
        }
    }

    companion object {
        const val TAG = "OrdersFragment"
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            OrdersFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}