package ru.yandex.practicum.sprint12koh10

import java.math.BigDecimal

data class ProductItem(
    val id: String,
    val name: String,
//    val priceInCents: Long, // копейки
    val price: BigDecimal,
) {
//    val price = priceInCents / 100
}

data class OrderItem(
    val productItem: ProductItem,
    val count: Int,
)

data class Order(
    val items: List<OrderItem>
) {

    val sum: BigDecimal = items.map { orderItem ->
        orderItem.productItem.price
            .multiply(BigDecimal.valueOf(orderItem.count.toLong()))
    }.sumOf { it }

    fun calculateSum(): BigDecimal {
        var sum = BigDecimal.ZERO
        items.forEach { orderItem ->
            sum += orderItem.productItem.price
                .multiply(BigDecimal.valueOf(orderItem.count.toLong()))
        }
        return sum
//        return items.fold(BigDecimal.ZERO) { sum, orderItem ->
//            sum + orderItem.productItem.price
//                .multiply(BigDecimal.valueOf(orderItem.count.toLong()))
//        }
//        return items.map {orderItem ->
//            orderItem.productItem.price
//                .multiply(BigDecimal.valueOf(orderItem.count.toLong()))
//        }.sumOf { it }
    }
}
