package com.pvsb.ecommercewhitelabel.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pvsb.core.model.ProductDTO
import com.pvsb.core.utils.*
import com.pvsb.ecommercewhitelabel.databinding.FavoriteListItemBinding
import com.pvsb.ecommercewhitelabel.presentation.activity.ActivityProductDetails

class FavoritesAdapter(
    private val onDelete: (ProductDTO) -> Unit
) : ListAdapter<ProductDTO, FavoritesAdapter.ViewHolder>(ListAdapterDiffUtil<ProductDTO>()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            FavoriteListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(
        private val binding: FavoriteListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ProductDTO) {
            binding.apply {

                val title = if (item.title.length > 10) {
                    "${item.title.take(15)} ..."
                } else {
                    item.title
                }

                tvProductName.text = title
                tvProductPrice.text = item.price.formatCurrency()

                Glide.with(itemView.context)
                    .load(item.imageUrl)
                    .centerCrop()
                    .into(ivProduct)

                itemView.setOnClickListener {
                    itemView.context.openActivity(ActivityProductDetails::class.java) {
                        it.putValueOnBundle(Constants.PRODUCT_DETAILS, item)
                    }
                }

                itemView.setOnLongClickListener {
                    onDelete.invoke(item)
                    true
                }
            }
        }
    }
}
