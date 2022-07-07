package com.pvsb.ecommercewhitelabel.presentation.activity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.pvsb.core.firebase.model.CartProductsDTO
import com.pvsb.core.firebase.model.PopulateCartDTO
import com.pvsb.core.firebase.model.ProductDTO
import com.pvsb.core.utils.*
import com.pvsb.core.utils.Constants.CART_ID
import com.pvsb.core.utils.Constants.Navigator.BOTTOM_NAV_CART
import com.pvsb.ecommercewhitelabel.databinding.ActivityProductDetailsBinding
import com.pvsb.ecommercewhitelabel.presentation.viewmodel.CartViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ActivityProductDetails : AppCompatActivity() {

    private lateinit var binding: ActivityProductDetailsBinding
    private val cartViewModel: CartViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpObservers()

        val data = intent?.getParcelableExtra<ProductDTO>("PRODUCT_NAME")

        data?.let {
            initialSetUp(it)
        } ?: kotlin.run {
            Snackbar.make(binding.ivProductImage, "There was an error", Snackbar.LENGTH_INDEFINITE)
                .setAction("Ok") {
                    finish()
                }
        }
    }

    private fun initialSetUp(product: ProductDTO) {
        binding.apply {

            tvProductPrice.text = product.price.formatCurrency()
            tvProductTitle.text = product.title

            Glide.with(this@ActivityProductDetails)
                .load(product.imageUrl)
                .centerCrop()
                .into(ivProductImage)

            btnBuy.setOnClickListener {
                val amount = tiProductAmount.editText?.text.toString().toInt()
                handleCart(product, amount)
            }
        }
    }

    private fun handleCart(product: ProductDTO, amount: Int) {

        lifecycleScope.launch {
            getValueDS(stringPreferencesKey(CART_ID)) {
                if (it.isNullOrEmpty()) {

                    val id = System.currentTimeMillis().toString()
                    val cartProduct = CartProductsDTO(
                        product, amount
                    )

                    val obj = PopulateCartDTO(
                        listOf(cartProduct)
                    )

                    cartViewModel.createCart(id, obj)

                    Toast.makeText(
                        this@ActivityProductDetails,
                        "cart created",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {

                    val obj = CartProductsDTO(
                        product, amount
                    )

                    cartViewModel.addProductToCart(it, obj)
                }
            }
        }
    }

    private fun setUpObservers() {
        cartViewModel.initialCart
            .flowWithLifecycle(lifecycle)
            .onEach { state ->
                handleResponse<String>(state, onSuccess = {
                    putValueDS(stringPreferencesKey(CART_ID), it)
                    closeActivityAndNavigate(
                        MainActivity(),
                        BOTTOM_NAV_CART
                    )
                }, {
                    Toast.makeText(
                        this@ActivityProductDetails,
                        it.message,
                        Toast.LENGTH_SHORT
                    ).show()
                })
            }.launchIn(lifecycleScope)

        cartViewModel.addProductToCart
            .flowWithLifecycle(lifecycle)
            .onEach { state ->
                handleResponse<Boolean>(state, onSuccess = {
                    closeActivityAndNavigate(
                        MainActivity(),
                        BOTTOM_NAV_CART
                    )
                }, onError = {
                    Toast.makeText(
                        this@ActivityProductDetails,
                        it.message,
                        Toast.LENGTH_SHORT
                    ).show()
                })
            }.launchIn(lifecycleScope)
    }
}