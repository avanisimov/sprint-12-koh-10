package ru.yandex.practicum.sprint12koh10

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import ru.yandex.practicum.sprint12koh10.databinding.VOrderItemBinding
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.Locale

class OrderItemViewHolder(
    parentView: ViewGroup,
    val binding: VOrderItemBinding = VOrderItemBinding.inflate(LayoutInflater.from(parentView.context))
) : ViewHolder(
    binding.root
) {

    fun bind(item: OrderItem) {
        val priceFormatter = NumberFormat.getCurrencyInstance()
        val price = priceFormatter.format(item.productItem.price)
//        val format = DecimalFormat()
//        format.maximumFractionDigits
        binding.name.text = "${item.productItem.name} ${price}"
        binding.count.text = item.count.toString()
    }
}