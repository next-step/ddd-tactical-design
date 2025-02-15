package kitchenpos.products.domain

import java.math.BigDecimal
import java.util.*

data class Product(
    val id: UUID,
    private val name: ProductName,
    private val price: ProductPrice,
) {
    fun getName(): String {
        return name.name
    }

    fun getPrice(): BigDecimal {
        return price.price
    }

    fun changePrice(price: BigDecimal): Product {
        return Product(
            id,
            name,
            ProductPrice.create(price)
        )
    }

    companion object {
        fun create(name: String, price: BigDecimal): Product {
            return Product(
                UUID.randomUUID(),
                ProductName.create(name),
                ProductPrice.create(price)
            )
        }
    }
}
