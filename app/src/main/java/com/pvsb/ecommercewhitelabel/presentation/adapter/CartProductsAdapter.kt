package com.pvsb.ecommercewhitelabel.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pvsb.core.model.CartProductsDTO
import com.pvsb.core.utils.*
import com.pvsb.core.utils.Constants.PRODUCT_DETAILS
import com.pvsb.ecommercewhitelabel.R
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

                tvProductName.text = item.product.title.formatLength()
                tvProductPrice.text = item.product.price.formatCurrency()
                tvProductQuantity.text = itemView.context.getString(
                    R.string.cart_button_list_item_amount_placeholder,
                    item.amount.toString()
                )
            }

            itemView.setOnClickListener {
                itemView.context.openActivity(ActivityProductDetails::class.java) {
                    it.putValueOnBundle(PRODUCT_DETAILS, item.product)
                }
            }

            itemView.setOnLongClickListener {
                onDelete.invoke(item)
                true
            }
        }
    }
}
