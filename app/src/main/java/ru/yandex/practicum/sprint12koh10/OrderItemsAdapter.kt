package ru.yandex.practicum.sprint12koh10

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

class OrderItemsAdapter : RecyclerView.Adapter<OrderItemViewHolder>() {

    private var items: List<OrderItem> = emptyList()

    var onPlusClickListener: OnPlusClickListener? = null
    var onMinusClickListener: OnMinusClickListener? = null

    fun setItems(newItems: List<OrderItem>) {
        val oldItems = items
        val diffUtil = object : DiffUtil.Callback() {
            override fun getOldListSize(): Int {
                return oldItems.size
            }

            override fun getNewListSize(): Int {
                return newItems.size
            }

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return oldItems[oldItemPosition].productItem.id == newItems[newItemPosition].productItem.id
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return oldItems[oldItemPosition] == newItems[newItemPosition]
            }
        }
        val diffResult = DiffUtil.calculateDiff(diffUtil)
        items = newItems
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderItemViewHolder {
        return OrderItemViewHolder(parent)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: OrderItemViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
        holder.binding.minus.setOnClickListener {
            onMinusClickListener?.onMinus(item)
        }
        holder.binding.plus.setOnClickListener {
            onPlusClickListener?.onPlus(item)
        }
    }

    fun interface OnPlusClickListener {
        fun onPlus(item: OrderItem)
    }

    fun interface OnMinusClickListener {
        fun onMinus(item: OrderItem)
    }
}