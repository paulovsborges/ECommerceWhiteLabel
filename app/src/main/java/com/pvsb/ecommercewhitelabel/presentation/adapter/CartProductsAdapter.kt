package com.pvsb.ecommercewhitelabel.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pvsb.core.firestore.model.CartProductsDTO
import com.pvsb.core.utils.Constants.PRODUCT_NAME
import com.pvsb.core.utils.ListAdapterDiffUtil
import com.pvsb.core.utils.formatCurrency
import com.pvsb.core.utils.openActivity
import com.pvsb.ecommercewhitelabel.databinding.CartListProductItemBinding
import com.pvsb.ecommercewhitelabel.presentation.activity.ActivityProductDetails

class CartProductsAdapter(
    private val onDelete: (CartProductsDTO) -> Unit
) :
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

    inner class ViewHolder(private val binding: CartListProductItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CartProductsDTO) {

            binding.apply {

                Glide.with(itemView.context)
                    .load(item.product.imageUrl)
                    .centerCrop()
                    .into(ivProduct)

                val title = if (item.product.title.length > 10) {
                    "${item.product.title.take(15)} ..."
                } else {
                    item.product.title
                }

                tvProductName.text = title
                tvProductPrice.text = item.product.price.formatCurrency()
            }

            itemView.setOnClickListener {
                itemView.context.openActivity(ActivityProductDetails::class.java) {
                    it.putExtra(PRODUCT_NAME, item.product)
                }
            }

            itemView.setOnLongClickListener {
                onDelete.invoke(item)
                true
            }
        }
    }
}