package kitchenpos.products.tobe.domain

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import java.math.BigDecimal

@Embeddable
data class ProductPrice(
    @Column(name = "price", nullable = false)
    val value: BigDecimal
) {
    init {
        require(value >= BigDecimal.ZERO) { "상품의 가격은 0보다 작을 수 없습니다" }
    }
}
