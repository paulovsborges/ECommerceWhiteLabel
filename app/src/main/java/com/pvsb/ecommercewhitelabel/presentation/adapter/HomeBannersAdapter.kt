package com.pvsb.ecommercewhitelabel.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pvsb.core.model.HomeBanner
import com.pvsb.core.model.ProductFilterCategories
import com.pvsb.core.utils.ListAdapterDiffUtil
import com.pvsb.ecommercewhitelabel.databinding.HomeBannerItemBinding

class HomeBannersAdapter(
    private val onBannerClick: (ProductFilterCategories) -> Unit
) : ListAdapter<HomeBanner, HomeBannersAdapter.ViewHolder>(ListAdapterDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            HomeBannerItemBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(
        private val binding: HomeBannerItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: HomeBanner) {
            binding.apply {
                tvTitle.text = item.categoryFilter.name

                Glide.with(itemView.context)
                    .load(item.image1)
                    .centerCrop()
                    .into(ivProductImage1)

                Glide.with(itemView.context)
                    .load(item.image2)
                    .centerCrop()
                    .into(ivProductImage2)

                itemView.setOnClickListener {
                    onBannerClick(item.categoryFilter)
                }
            }
        }
    }
}
