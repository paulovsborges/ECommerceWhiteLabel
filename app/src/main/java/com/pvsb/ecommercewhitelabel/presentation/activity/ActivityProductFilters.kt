package com.pvsb.ecommercewhitelabel.presentation.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.pvsb.core.utils.MockFactory
import com.pvsb.ecommercewhitelabel.databinding.ActivityProductFiltersBinding
import com.pvsb.ecommercewhitelabel.presentation.adapter.ProductFilterAdapter

class ActivityProductFilters : AppCompatActivity() {

    private lateinit var binding: ActivityProductFiltersBinding
    private val mAdapter = ProductFilterAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductFiltersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initialSetup()
    }

    private fun initialSetup() {

        mAdapter.submitList(MockFactory.Filters.list)
        binding.rvMain.adapter = mAdapter
    }
}