package com.pvsb.ecommercewhitelabel.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pvsb.core.model.UserAddressDTO
import com.pvsb.core.utils.ListAdapterDiffUtil
import com.pvsb.ecommercewhitelabel.databinding.AddressesListItemBinding

class AddressesAdapter(
    private val onDelete: (UserAddressDTO) -> Unit,
    private val onClick: (UserAddressDTO) -> Unit
) : ListAdapter<UserAddressDTO, AddressesAdapter.ViewHolder>(ListAdapterDiffUtil<UserAddressDTO>()) {

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
            }

            itemView.setOnClickListener {
                onClick.invoke(item)
            }

            itemView.setOnLongClickListener {
                onDelete.invoke(item)
                true
            }
        }
    }
}
