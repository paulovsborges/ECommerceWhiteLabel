package com.pvsb.ecommercewhitelabel.presentation.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.pvsb.core.firestore.model.ProductFilters
import com.pvsb.core.utils.MockFactory
import com.pvsb.ecommercewhitelabel.databinding.ActivityProductFiltersBinding
import com.pvsb.ecommercewhitelabel.presentation.adapter.ProductFilterAdapter
import com.pvsb.ecommercewhitelabel.presentation.viewmodel.FiltersViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ActivityProductFilters : AppCompatActivity() {

    private lateinit var binding: ActivityProductFiltersBinding
    private val mAdapter = ProductFilterAdapter(::onFilterSelected)
    private val viewModel: FiltersViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductFiltersBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initialSetup()
    }

    private fun initialSetup() {
        mAdapter.submitList(MockFactory.Filters.list)
        binding.rvMain.adapter = mAdapter

        binding.btnApplyFilters.setOnClickListener {

            val test = viewModel.selectedFilters.size.toString()

            val intent = Intent().apply {
                putExtra(this@ActivityProductFilters.javaClass.simpleName, test)
            }

            setResult(Activity.RESULT_OK, intent)

            finish()
        }
    }

    private fun onFilterSelected(item: ProductFilters) {
        viewModel.handleFilterSelection(item)
    }
}