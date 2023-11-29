package ru.yandex.practicum.sprint12koh10

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import ru.yandex.practicum.sprint12koh10.databinding.ActivityMainBinding
import java.math.BigDecimal
import java.text.NumberFormat
import java.util.UUID
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private val orderItemsAdapter = OrderItemsAdapter()

    val cart: Cart by lazy {
        Cart(this)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.items.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = orderItemsAdapter
        }

        orderItemsAdapter.setItems(cart.getItems())
        cart.onItemsChangedListener = Cart.OnItemsChangedListener {
            orderItemsAdapter.setItems(it.items)

            val i : Int? = null
            i?.let {

            }
            binding.sum.text = it.calculateSum().let {
                NumberFormat.getCurrencyInstance().format(it)
            }
        }

        binding.toolbar.apply {
            inflateMenu(R.menu.m_main)
            setOnMenuItemClickListener { menuItem ->
                if (menuItem.itemId == R.id.load_data) {
                    val order = getOrderFromServer()
                    orderItemsAdapter.setItems(order.items)
                    cart.saveOrder(order)
                }
                true
            }
        }

        orderItemsAdapter.onPlusClickListener = OrderItemsAdapter.OnPlusClickListener { item ->
            cart.plus(item)
        }
        orderItemsAdapter.onMinusClickListener = OrderItemsAdapter.OnMinusClickListener {item ->
            cart.minus(item)
        }

        binding.clear.setOnClickListener {
            cart.clear()
        }

    }

    private fun getOrderFromServer(): Order {
        return Order(
            items = (1..5).map {
                OrderItem(
                    productItem = ProductItem(
                        id = UUID.randomUUID().toString(),
                        name = "Товар $it",
                        price = BigDecimal(Random.nextInt(50, 200).div(100.0))
                    ),
                    count = 1
                )
            }
        )
    }
}