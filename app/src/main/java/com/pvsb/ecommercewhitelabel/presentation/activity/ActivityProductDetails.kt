package com.pvsb.ecommercewhitelabel.presentation.activity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.pvsb.core.firestore.model.ProductDTO
import com.pvsb.core.utils.Constants.CART_ID
import com.pvsb.core.utils.getValueFromDataStore
import com.pvsb.core.utils.putValueOnDataStore
import com.pvsb.ecommercewhitelabel.databinding.ActivityProductDetailsBinding
import com.pvsb.ecommercewhitelabel.presentation.viewmodel.CartViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

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

        binding.apply {
            tvProductPrice.text = data?.price.toString()
            tvProductTitle.text = data?.title

            Glide.with(this@ActivityProductDetails)
                .load(data?.imageUrl)
                .centerCrop()
                .into(ivProductImage)

            btnBuy.setOnClickListener {
                handleCart()
            }
        }
    }

    private fun handleCart() {

        lifecycleScope.launch {
            getValueFromDataStore(stringPreferencesKey(CART_ID), "") {
                if (it.isEmpty()) {
                    cartViewModel.createCart()
                } else {
                    Toast.makeText(
                        this@ActivityProductDetails,
                        "cart already created",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun setUpObservers() {
        cartViewModel.cartId.observe(this) {
            lifecycleScope.launch {
                putValueOnDataStore(stringPreferencesKey(CART_ID), it)
            }
        }
    }
}