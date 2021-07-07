package com.gumi.projectgumi.ui.cart

import android.app.AlertDialog
import android.content.Intent
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.gumi.gumiproject8.utils.setVisible
import com.gumi.projectgumi.MainActivity
import com.gumi.projectgumi.R
import com.gumi.projectgumi.adapter.CartAdapter
import com.gumi.projectgumi.base.BaseFragment
import com.gumi.projectgumi.data.model.CartModel
import com.gumi.projectgumi.databinding.FragmentCartBinding
import com.gumi.projectgumi.ui.checkout.CheckoutFragment
import com.gumi.projectgumi.ui.login.LoginActivity
import com.gumi.projectgumi.utils.BillingSubcribe
import com.gumi.projectgumi.utils.Utils.showDialogFragment
import com.gumi.projectgumi.viewmodel.CartViewModel
import com.gumi.projectgumi.viewmodel.LoginViewModel
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

        cartViewModel.dataCart.observe(requireActivity()) { it ->
            it?.let { listCartModel ->
                countList = listCartModel.size
                listCart = listCartModel

                cartAdapter.submitList(listCart)
                BillingSubcribe.getInstance(TAG, requireContext()).isSubscribe.observe(
                    requireActivity()
                ) { isActive ->
                    isActive?.let {
                        cartViewModel.sumMoneyCart(listCart, it)
                        binding.imageSale.setVisible(it)
                    }
                }
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
            cartViewModel.updateAmount(cartId, amount + 1)
    }

    private fun clickReduction(cartId: Int, position: Int) {
        val amount = listCart[position].amount
        if (amount > 1)
            cartViewModel.updateAmount(cartId, amount - 1)
    }

    private fun loadData() {
        cartViewModel.loadDataCart()
    }

    companion object {
        const val DELETE = "delete"
        const val INCREASE = "increase"
        const val REDUCTION = "reduction"
        const val TAG = "LogCartFragment"
    }

}