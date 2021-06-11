package com.example.projectgumi.ui.order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projectgumi.MainActivity
import com.example.projectgumi.R
import com.example.projectgumi.adapter.OrdersAdapter
import com.example.projectgumi.databinding.FragmentOrdersBinding
import com.example.projectgumi.viewmodel.OrdersViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import org.koin.androidx.viewmodel.ext.android.viewModel

class OrdersFragment : DialogFragment() {

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
            btnOrders.setOnClickListener {
                dismiss()
                if(context is MainActivity){
                    (context as MainActivity).goToFragment(MainActivity.SHOP)
                }
            }
            adapterOrders.adapter = ordersAdapter
            adapterOrders.layoutManager = LinearLayoutManager(requireContext())
        }
        Firebase.auth.currentUser?.let {
            viewModel.getOrdersByIdUser(it.uid)
        }
        viewModel.dataOrders.observe(requireActivity()){
            ordersAdapter.submitList(it)
        }
    }

    companion object {
        const val TAG = "OrdersFragment"
    }
}