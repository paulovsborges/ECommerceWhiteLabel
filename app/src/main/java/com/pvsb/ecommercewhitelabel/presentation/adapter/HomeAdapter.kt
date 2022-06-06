package com.pvsb.ecommercewhitelabel.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pvsb.ecommercewhitelabel.data.model.MainHomeModel
import com.pvsb.ecommercewhitelabel.databinding.ListItemBinding
import com.pvsb.ecommercewhitelabel.utils.ListAdapterDiffUtil

class HomeAdapter(
    private val onItemClick: (MainHomeModel) -> Unit
) : ListAdapter<MainHomeModel, HomeAdapter.ViewHolder>(ListAdapterDiffUtil<MainHomeModel>()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: ListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: MainHomeModel) {

            binding.apply {
                tvProductTitle.text = item.title
                tvProductPrice.text = item.price
            }

            itemView.setOnClickListener {
                onItemClick.invoke(item)
            }
        }
    }
}