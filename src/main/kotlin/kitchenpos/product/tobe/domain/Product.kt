package kitchenpos.product.tobe.domain

import jakarta.persistence.Column
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.math.BigDecimal
import java.util.*

@Table(name = "product")
@Entity
class Product(
    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    var id: UUID? = null,

    @Embedded
    var productName: ProductName,

    @Column(name = "price", nullable = false)
    var price: BigDecimal,
) {

    init {
        require(price >= BigDecimal.ZERO) { "상품 가격은 0원 이상이어야 합니다." }
    }

    fun changePrice(price: BigDecimal) {
        require(price >= BigDecimal.ZERO) { "상품 가격은 0원 이상이어야 합니다." }
        this.price = price
    }
}
