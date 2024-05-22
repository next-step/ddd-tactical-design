package kitchenpos.products.tobe.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.math.BigDecimal
import java.util.*

@Table(name = "product")
@Entity
class Product(
    @Column(name = "name", nullable = false)
    val displayedName: String,
    price: BigDecimal
) {
    @Id
    @Column(name = "id", columnDefinition = "binary(16)")
    val id: UUID = UUID.randomUUID()

    @Column(name = "price", nullable = false)
    var price: BigDecimal = price
        private set

    init {
        validatePrice()
    }

    fun changePrice(price: BigDecimal) {
        validatePrice()

        this.price = price
    }

    private fun validatePrice() = require(price >= BigDecimal.ZERO) { "상품의 가격은 0보다 작을 수 없습니다" }
}
