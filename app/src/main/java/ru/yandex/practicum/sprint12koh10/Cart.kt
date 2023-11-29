package ru.yandex.practicum.sprint12koh10

import android.content.Context
import android.util.Log
import com.google.gson.Gson

class Cart(context: Context) {

    private val sharedPreferences =
        context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE)
    private val gson = Gson()

    var onItemsChangedListener: OnItemsChangedListener? = null

    fun getItems(): List<OrderItem> {
        val json: String? = sharedPreferences.getString(KEY_ORDER, null)
        return if (json != null) {
            val order: Order = gson.fromJson(json, Order::class.java)
            order.items
        } else {
            emptyList()
        }
    }

    fun saveOrder(order: Order) {
        val json: String = gson.toJson(order)
        sharedPreferences.edit()
            .putString(KEY_ORDER, json)
            .apply()
        onItemsChangedListener?.onItemsChanged(order)
    }

    fun plus(item: OrderItem) {
        val json: String? = sharedPreferences.getString(KEY_ORDER, null)
        val order: Order? = json?.let { gson.fromJson(it, Order::class.java) }
        if (order != null) {
            val mutableItems = order.items.toMutableList()
            val index = mutableItems
                .indexOfFirst { it.productItem.id == item.productItem.id }
            val oldItem = mutableItems[index]
            mutableItems[index] = oldItem.copy(
                count = oldItem.count + 1
            )
            val newOrder = order.copy(
                items = mutableItems
            )
            saveOrder(newOrder)
        }
    }

    fun minus(item: OrderItem) {
        val json: String? = sharedPreferences.getString(KEY_ORDER, null)
        val order: Order? = json?.let { gson.fromJson(it, Order::class.java) }
        if (order != null) {
            val mutableItems = order.items.toMutableList()
            val index = mutableItems
                .indexOfFirst { it.productItem.id == item.productItem.id }
            val oldItem = mutableItems[index]
            if (oldItem.count == 1) {
                mutableItems.removeAt(index)
            } else {
                mutableItems[index] = oldItem.copy(
                    count = oldItem.count - 1
                )
            }
            val newOrder = order.copy(
                items = mutableItems
            )
            saveOrder(newOrder)
        }
    }

    fun clear() {
        sharedPreferences.edit()
            .clear()
            .apply()
        onItemsChangedListener?.onItemsChanged(Order(emptyList()))
    }

    companion object {
        const val SHARED_PREFS_NAME = "cart"
        const val KEY_ORDER = "order"
    }


    fun interface OnItemsChangedListener {
        fun onItemsChanged(order: Order)
    }
}