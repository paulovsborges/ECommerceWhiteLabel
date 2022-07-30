package com.pvsb.ecommercewhitelabel.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pvsb.core.model.UserAddressDTO
import com.pvsb.core.utils.ListAdapterDiffUtil
import com.pvsb.ecommercewhitelabel.R
import com.pvsb.ecommercewhitelabel.databinding.AddressesListItemBinding

class SelectAddressAdapter(
    private val onDetailsClick: (UserAddressDTO) -> Unit
) : ListAdapter<UserAddressDTO, SelectAddressAdapter.ViewHolder>(ListAdapterDiffUtil<UserAddressDTO>()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            AddressesListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(
        private val binding: AddressesListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: UserAddressDTO) {
            binding.apply {
                tvAddressNick.text = item.addressNick
                tvCity.text = item.city
                tvNeighbour.text = item.neighbour

                ivArrow.setOnClickListener {
                    onDetailsClick.invoke(item)
                }

                root.strokeColor = ResourcesCompat.getColor(
                    itemView.context.resources,
                    R.color.grey,
                    null
                )

                if (item.isChecked) {
                    root.strokeColor = ResourcesCompat.getColor(
                        itemView.context.resources,
                        R.color.red_vivid,
                        null
                    )
                }
            }

            itemView.setOnClickListener {
                selectAddress(adapterPosition)
            }
        }
    }

    private fun selectAddress(position: Int) {
        currentList.forEach {
            it.isChecked = false
        }

        currentList[position].isChecked = true
        notifyDataSetChanged()
    }
}
