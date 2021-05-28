package com.example.projectgumi.ui.cart

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.projectgumi.R
import com.example.projectgumi.adapter.CartAdapter
import com.example.projectgumi.databinding.FragmentCartBinding
import com.example.projectgumi.ui.checkout.CheckoutFragment
import com.example.projectgumi.utils.Utils.showDialogFragment
import com.example.projectgumi.viewmodel.CartViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class CartFragment : Fragment() {
    private lateinit var binding: FragmentCartBinding
    private val cartViewModel by viewModel<CartViewModel>()
    private lateinit var cartAdapter: CartAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =
            FragmentCartBinding.bind(inflater.inflate(R.layout.fragment_cart, container, false))
        cartAdapter = CartAdapter()

        binding.apply {
            lifecycleOwner = this@CartFragment
            model = cartViewModel
            adapterCart = cartAdapter
            btnCart.setOnClickListener {
                val tag = CheckoutFragment.TAG
                showDialogFragment(activity, CheckoutFragment(), tag)
            }
        }

        cartViewModel.loadCart()

        cartViewModel.resultCart.observe(requireActivity()) {
            it?.let {
                cartAdapter.submitList(it)
                var money = 0.0
                it.forEach {
                    val price = it.product.productMoney.substring(1).toDouble()
                    money += price*it.unit.toDouble()
                }
                cartViewModel.sumMyMoney(money.toString())
            }
        }

        return binding.root
    }
}