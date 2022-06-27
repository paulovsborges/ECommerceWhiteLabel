package com.pvsb.ecommercewhitelabel.presentation.activity

import android.os.Bundle
import android.widget.Toast
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
            Toast.makeText(this, "${viewModel.selectedFilters.size}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun onFilterSelected(item: ProductFilters) {
        viewModel.handleFilterSelection(item)
    }
}