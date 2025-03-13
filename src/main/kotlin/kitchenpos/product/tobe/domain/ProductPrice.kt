package kitchenpos.product.tobe.domain

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import java.math.BigDecimal

@Embeddable
class ProductPrice(
    @Column(name = "price", nullable = false)
    val price: BigDecimal

) {

    init {
        require(price >= BigDecimal.ZERO) { "상품 가격은 0원 이상이어야 합니다." }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ProductPrice

        return price == other.price
    }

    override fun hashCode(): Int {
        return price.hashCode()
    }
}
