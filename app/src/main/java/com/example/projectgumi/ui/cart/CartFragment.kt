package com.example.projectgumi.ui.cart

import android.app.AlertDialog
import android.content.Intent
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projectgumi.MainActivity
import com.example.projectgumi.R
import com.example.projectgumi.adapter.CartAdapter
import com.example.projectgumi.base.BaseFragment
import com.example.projectgumi.data.model.CartModel
import com.example.projectgumi.databinding.FragmentCartBinding
import com.example.projectgumi.ui.checkout.CheckoutFragment
import com.example.projectgumi.ui.login.LoginActivity
import com.example.projectgumi.utils.Utils.showDialogFragment
import com.example.projectgumi.viewmodel.CartViewModel
import com.example.projectgumi.viewmodel.LoginViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class CartFragment : BaseFragment<FragmentCartBinding>() {

    override val layoutResource: Int
        get() = R.layout.fragment_cart

    private val cartViewModel by viewModel<CartViewModel>()
    private lateinit var cartAdapter: CartAdapter
    private val loginViewModel by viewModel<LoginViewModel>()
    private var listCart = mutableListOf<CartModel>()

    override fun onResume() {
        super.onResume()
        loadData()
    }

    override fun viewCreated() {
        var countList = 0

        cartAdapter = CartAdapter { id, name, pos ->
            clickItem(id, name, pos)
        }

        binding.apply {
            model = cartViewModel

            btnCart.setOnClickListener {
                if (currentUser == null) {
                    val intent = Intent(requireContext(), LoginActivity::class.java)
                    startActivity(intent)
                    (context as MainActivity).finish()
                } else {
                    currentUser?.let {
                        loginViewModel.checkUser(it.uid)
                    }
                }
            }

            adapterMyCart.apply {
                adapter = cartAdapter
                val llm = LinearLayoutManager(requireContext())
                layoutManager = llm
            }
        }

        loadData()

        cartViewModel.dataCart.observe(requireActivity()) {
            it?.let {
                countList = it.size
                listCart = it
                cartAdapter.submitList(listCart)
                cartViewModel.sumMoneyCart(listCart)
            }
        }

        loginViewModel.status.observe(this) {
            it?.let {
                when (it) {
                    "phone" -> {
                        (context as MainActivity).loginPhoneNumber()
                    }
                    "profile" -> {
                        (context as MainActivity).loginProfile()
                    }
                    "login" -> {
                        showDialogFragment(
                            activity,
                            CheckoutFragment.newInstance(
                                cartViewModel.sumMoney.value!!,
                                countList.toString()
                            ),
                            CheckoutFragment.TAG
                        )
                    }
                }
            }
        }

    }

    private fun clickItem(cartId: Int, name: String, position: Int) {
        when (name) {
            DELETE -> {
                clickDelete(cartId)
            }
            INCREASE -> {
                clickIncrease(cartId, position)
            }
            REDUCTION -> {
                clickReduction(cartId, position)
            }
        }
    }

    private fun clickDelete(cartId: Int) {
        val dialog = AlertDialog.Builder(requireContext())
        dialog.setMessage("Do you want delete?")
        dialog.setPositiveButton("Yes") { d, _ ->
            cartViewModel.deleteCart(cartId)
            d.dismiss()
        }
        dialog.setNegativeButton("No") { d, _ ->
            d.cancel()
        }
        val alertDialog = dialog.create()
        alertDialog.show()
    }

    private fun clickIncrease(cartId: Int, position: Int) {
        val amount = listCart[position].amount
        if (amount < 10)
            cartViewModel.updateAmount(cartId, amount+1)
    }

    private fun clickReduction(cartId: Int, position: Int) {
        val amount = listCart[position].amount
        if (amount > 1)
            cartViewModel.updateAmount(cartId, amount-  1)
    }

    fun loadData() {
        cartViewModel.loadDataCart()
    }

    companion object {
        const val DELETE = "delete"
        const val INCREASE = "increase"
        const val REDUCTION = "reduction"
    }

}