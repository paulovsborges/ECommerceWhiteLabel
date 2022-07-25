package com.pvsb.ecommercewhitelabel.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pvsb.core.model.ProductFilterCategories
import com.pvsb.core.model.ProductFilters
import com.pvsb.core.utils.ListAdapterDiffUtil
import com.pvsb.ecommercewhitelabel.databinding.FiltersListItemBinding

class ProductFilterAdapter(
    private val filterSelected: (ProductFilterCategories) -> Unit
) :
    ListAdapter<ProductFilterCategories, ProductFilterAdapter.ViewHolder>(ListAdapterDiffUtil<ProductFilterCategories>()) {

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

    inner class ViewHolder(private val binding: FiltersListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ProductFilterCategories) {

            binding.apply {

                tvFilterText.text = item.name
                cbFilterSelection.isChecked = item.isChecked

                cbFilterSelection.setOnCheckedChangeListener { _, checked ->
                    item.isChecked = checked
                }

                itemView.setOnClickListener {
                    cbFilterSelection.isChecked = !item.isChecked
                    filterSelected.invoke(item)
                    notifyDataSetChanged()
                }
            }
        }
    }
}