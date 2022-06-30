package com.pvsb.ecommercewhitelabel.presentation.activity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.pvsb.core.firestore.model.CartProductsDTO
import com.pvsb.core.firestore.model.PopulateCartDTO
import com.pvsb.core.firestore.model.ProductDTO
import com.pvsb.core.utils.Constants.CART_ID
import com.pvsb.core.utils.Constants.Navigator.BOTTOM_NAV_CART
import com.pvsb.core.utils.closeActivityAndNavigate
import com.pvsb.core.utils.getValueDS
import com.pvsb.core.utils.putValueDS
import com.pvsb.ecommercewhitelabel.databinding.ActivityProductDetailsBinding
import com.pvsb.ecommercewhitelabel.presentation.viewmodel.CartViewModel
import dagger.hilt.android.AndroidEntryPoint
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

            tvProductPrice.text = product.price.toString()
            tvProductTitle.text = product.title

            Glide.with(this@ActivityProductDetails)
                .load(product.imageUrl)
                .centerCrop()
                .into(ivProductImage)

            btnBuy.setOnClickListener {

                handleCart(product.id, 1)
            }
        }
    }

    private fun handleCart(productId: Int, amount: Int) {

        lifecycleScope.launch {
            getValueDS(stringPreferencesKey(CART_ID)) {
                if (it.isNullOrEmpty()) {

                    val product = CartProductsDTO(
                        productId, amount
                    )

                    val obj = PopulateCartDTO(
                        listOf(product)
                    )

                    cartViewModel.createCart(obj)
                    Toast.makeText(
                        this@ActivityProductDetails,
                        "cart created",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {

                    val obj = CartProductsDTO(
                        productId, amount
                    )

                    cartViewModel.addProductToCart(it, obj)
                }
            }
        }
    }

    private fun setUpObservers() {
        cartViewModel.initialCart.observe(this) {
            lifecycleScope.launch {

                putValueDS(stringPreferencesKey(CART_ID), it)

                closeActivityAndNavigate(
                    MainActivity(),
                    BOTTOM_NAV_CART
                )
            }
        }

        cartViewModel.populateCart.observe(this) {
            closeActivityAndNavigate(
                MainActivity(),
                BOTTOM_NAV_CART
            )
        }
    }
}