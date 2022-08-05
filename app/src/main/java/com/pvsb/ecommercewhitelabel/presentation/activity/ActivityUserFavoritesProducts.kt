package com.pvsb.ecommercewhitelabel.presentation.activity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.pvsb.core.model.ProductDTO
import com.pvsb.core.utils.Constants.PrefsKeys.USER_ID
import com.pvsb.core.utils.getValueDS
import com.pvsb.core.utils.handleResponse
import com.pvsb.core.utils.setSharedViewAnim
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
        setSharedViewAnim("sharedElementOnRightSide")
        super.onCreate(savedInstanceState)
        binding = ActivityUserFavoritesProductsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initialSetUp()
    }

    private fun initialSetUp() {

        binding.apply {
            rvFavoritesList.adapter = mAdapter

            ivBack.setOnClickListener {
                onBackPressed()
            }
        }

        lifecycleScope.launch {
            getValueDS(stringPreferencesKey(USER_ID)) {
                it?.let {
                    getFavoritesProducts(it)
                }
            }
        }
    }

    private fun getFavoritesProducts(userId: String) {
        viewModel.getFavoriteProducts(userId)
            .flowWithLifecycle(lifecycle)
            .onEach { state ->
                handleResponse<List<ProductDTO>>(
                    state,
                    onSuccess = {
                        binding.clMainContent.visibility = View.VISIBLE
                        binding.vfMain.displayedChild = DATA_STATE
                        mAdapter.submitList(it)
                    }, onError = {
                }, onEmpty = {
                    binding.clMainContent.visibility = View.VISIBLE
                    binding.vfMain.displayedChild = EMPTY_STATE
                }
                )
            }
            .launchIn(lifecycleScope)
    }

    private fun deleteProductFromFavorites(product: ProductDTO) {
        lifecycleScope.launch {
            getValueDS(stringPreferencesKey(USER_ID)) {
                it?.let { userId ->
                    viewModel.deleteProductToUserFavorites(userId, product)
                        .flowWithLifecycle(lifecycle)
                        .onEach { state ->
                            handleResponse<Unit>(
                                state,
                                onSuccess = {
                                    getFavoritesProducts(userId)
                                }, onError = {
                            }, onEmpty = {
                                Toast.makeText(
                                    this@ActivityUserFavoritesProducts,
                                    "empty state",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            )
                        }
                        .launchIn(lifecycleScope)
                }
            }
        }
    }

    companion object {
        const val DATA_STATE = 0
        const val EMPTY_STATE = 1
    }
}
