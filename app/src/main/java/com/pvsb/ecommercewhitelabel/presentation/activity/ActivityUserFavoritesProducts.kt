package com.pvsb.ecommercewhitelabel.presentation.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.pvsb.core.firebase.model.ProductDTO
import com.pvsb.core.utils.Constants.PrefsKeys.USER_ID
import com.pvsb.core.utils.getValueDS
import com.pvsb.core.utils.handleResponse
import com.pvsb.ecommercewhitelabel.R
import com.pvsb.ecommercewhitelabel.databinding.ActivityUserFavoritesProductsBinding
import com.pvsb.ecommercewhitelabel.presentation.adapter.FavoritesAdapter
import com.pvsb.ecommercewhitelabel.presentation.viewmodel.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ActivityUserFavoritesProducts : AppCompatActivity() {

    private lateinit var binding: ActivityUserFavoritesProductsBinding
    private val viewModel: ProfileViewModel by viewModels()
    private val mAdapter = FavoritesAdapter(::deleteProductFromFavorites)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserFavoritesProductsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initialSetUp()
    }

    private fun initialSetUp() {

        binding.apply {
            rvFavoritesList.adapter = mAdapter

            ivBack.setOnClickListener {
                finish()
            }
        }

        lifecycleScope.launch {
            getValueDS(stringPreferencesKey(USER_ID)) {
                it?.let {
                    viewModel.getFavoriteProducts(it)
                }
            }
        }

        initObservers()
    }

    private fun initObservers() {
        viewModel.userFavoriteProducts
            .flowWithLifecycle(lifecycle)
            .onEach { state ->
                handleResponse<List<ProductDTO>>(state,
                    onSuccess = {
                        mAdapter.submitList(it)
//                        mAdapter.notifyItemRangeChanged(mAdapter.currentList.size, it.size)
                    }, onError = {

                    }, onEmpty = {
                        Toast.makeText(this, "empty state", Toast.LENGTH_SHORT).show()
                    })
            }
            .launchIn(lifecycleScope)
    }

    private fun deleteProductFromFavorites(product: ProductDTO) {

        lifecycleScope.launch {
            getValueDS(stringPreferencesKey(USER_ID)) {
                it?.let {
                    viewModel.deleteProductToUserFavorites(it, product)
                }
            }
        }
    }
}