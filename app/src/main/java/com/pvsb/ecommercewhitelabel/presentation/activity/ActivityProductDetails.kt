package com.pvsb.ecommercewhitelabel.presentation.activity

import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.transition.platform.MaterialContainerTransform
import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback
import com.pvsb.core.model.CartProductsDTO
import com.pvsb.core.model.PopulateCartDTO
import com.pvsb.core.model.ProductDTO
import com.pvsb.core.utils.*
import com.pvsb.core.utils.Constants.Navigator.BOTTOM_NAV_CART
import com.pvsb.core.utils.Constants.Navigator.BOTTOM_NAV_PROFILE
import com.pvsb.core.utils.Constants.PRODUCT_DETAILS
import com.pvsb.core.utils.Constants.PrefsKeys.CART_ID
import com.pvsb.core.utils.Constants.PrefsKeys.USER_ID
import com.pvsb.ecommercewhitelabel.R
import com.pvsb.ecommercewhitelabel.databinding.ActivityProductDetailsBinding
import com.pvsb.ecommercewhitelabel.presentation.viewmodel.CartViewModel
import com.pvsb.ecommercewhitelabel.presentation.viewmodel.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ActivityProductDetails : AppCompatActivity() {

    private lateinit var binding: ActivityProductDetailsBinding
    private val cartViewModel: CartViewModel by viewModels()
    private val profileViewModel: ProfileViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val data = getValueFromBundle<ProductDTO>(PRODUCT_DETAILS)

        data?.let {
            initialSetUp(it)
        } ?: kotlin.run {
            Snackbar.make(
                binding.ivProductImage,
                getText(R.string.error_generic_message),
                Snackbar.LENGTH_INDEFINITE
            ).setAction(getText(R.string.ok_message)) {
                finish()
            }.show()
        }
    }

    private fun initialSetUp(product: ProductDTO) {
        binding.apply {
            ivBack.setOnClickListener { onBackPressed() }
            tvProductPrice.text = product.price.formatCurrency()
            tvProductTitle.text = product.title

            Glide.with(this@ActivityProductDetails)
                .load(product.imageUrl)
                .centerCrop()
                .into(ivProductImage)

            btnBuy.setOnClickListener {
                val amount = tiProductAmount.editText?.text.toString().toInt()
                handleProductAdditionToCart(product, amount)
            }

            ivFavorite.setOnClickListener {
                lifecycleScope.launch {
                    getValueDS(stringPreferencesKey(USER_ID)) {
                        it?.let { userId ->
                            profileViewModel.addProductToUserFavorites(userId, product)

                            Snackbar.make(
                                binding.root,
                                getText(R.string.product_added_to_favorites),
                                Snackbar.LENGTH_LONG
                            ).setAction(getText(R.string.ok_message)) {
                                closeActivityAndNavigate(
                                    MainActivity(),
                                    BOTTOM_NAV_PROFILE
                                )
                            }.show()
                        } ?: kotlin.run {

                            Snackbar.make(
                                binding.root,
                                getText(R.string.need_to_login_first),
                                Snackbar.LENGTH_LONG
                            ).setAction(getText(R.string.need_to_login_first_action)) {
                                closeActivityAndNavigate(
                                    MainActivity(),
                                    BOTTOM_NAV_PROFILE
                                )
                            }.show()
                        }
                    }
                }
            }
        }
    }

    private fun handleProductAdditionToCart(product: ProductDTO, amount: Int) {
        lifecycleScope.launch {
            getValueDS(stringPreferencesKey(CART_ID)) {
                if (it.isNullOrEmpty()) {
                    val id = System.currentTimeMillis().toString()
                    val cartProduct = CartProductsDTO(product, amount)
                    val obj = PopulateCartDTO(listOf(cartProduct))
                    createCart(id, obj)
                } else {
                    val obj = CartProductsDTO(product, amount)
                    addProductToCart(it, obj)
                }
            }
        }
    }

    private fun createCart(cartId: String, product: PopulateCartDTO) {
        cartViewModel.createCart(cartId, product)
            .flowWithLifecycle(lifecycle)
            .onEach { state ->
                handleResponse<String>(state, onSuccess = { cartId ->
                    putValueDS(stringPreferencesKey(CART_ID), cartId)
                    closeActivityAndNavigate(
                        MainActivity(),
                        BOTTOM_NAV_CART
                    )
                }, { e ->
                    Toast.makeText(
                        this@ActivityProductDetails,
                        e.message,
                        Toast.LENGTH_SHORT
                    ).show()
                })
            }.launchIn(lifecycleScope)
    }

    private fun addProductToCart(cartId: String, product: CartProductsDTO) {
        cartViewModel.addProductToCart(cartId, product)
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
