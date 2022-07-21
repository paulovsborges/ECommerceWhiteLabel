package com.pvsb.ecommercewhitelabel.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pvsb.core.model.OderModelReqDTO
import com.pvsb.core.utils.ListAdapterDiffUtil
import com.pvsb.ecommercewhitelabel.R
import com.pvsb.ecommercewhitelabel.databinding.OrderListItemBinding

class OrdersAdapter(
    private val onOrderLicked: (OderModelReqDTO) -> Unit
) : ListAdapter<OderModelReqDTO, OrdersAdapter.ViewHolder>(ListAdapterDiffUtil<OderModelReqDTO>()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            OrderListItemBinding.inflate(
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
        private val binding: OrderListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        private val context = itemView.context

        fun bind(item: OderModelReqDTO) {
            binding.apply {
                tvOrderId.text = context.getString(
                    R.string.orders_list_item_order_label,
                    (adapterPosition + 1).toString()
                )
            }

            itemView.setOnClickListener {
                onOrderLicked.invoke(item)
            }
        }
    }
}