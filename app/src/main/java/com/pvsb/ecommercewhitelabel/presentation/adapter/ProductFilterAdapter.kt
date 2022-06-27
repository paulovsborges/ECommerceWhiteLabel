package com.pvsb.ecommercewhitelabel.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pvsb.core.firestore.model.ProductFilters
import com.pvsb.core.utils.ListAdapterDiffUtil
import com.pvsb.ecommercewhitelabel.databinding.FiltersListItemBinding

class ProductFilterAdapter :
    ListAdapter<ProductFilters, ProductFilterAdapter.ViewHolder>(ListAdapterDiffUtil<ProductFilters>()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            FiltersListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(private val binding: FiltersListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ProductFilters) {

            binding.apply {

                tvFilterText.text = item.name
                cbFilterSelection.isChecked = item.isChecked

                cbFilterSelection.setOnCheckedChangeListener { _, checked ->
                    item.isChecked = checked
                }
            }
        }
    }
}