package com.pvsb.ecommercewhitelabel.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pvsb.core.firestore.model.CartProductsDTO
import com.pvsb.core.utils.ListAdapterDiffUtil
import com.pvsb.core.utils.formatCurrency
import com.pvsb.ecommercewhitelabel.databinding.CartListProductItemBinding

class CartProductsAdapter :
    ListAdapter<CartProductsDTO, CartProductsAdapter.ViewHolder>(ListAdapterDiffUtil<CartProductsDTO>()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            CartListProductItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(private val binding: CartListProductItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CartProductsDTO) {

            binding.apply {

                Glide.with(itemView.context)
                    .load(item.product.imageUrl)
                    .centerInside()
                    .centerCrop()
                    .into(ivProduct)

                tvProductName.text = item.product.title
                tvProductPrice.text = item.product.price.formatCurrency()
            }
        }
    }
}