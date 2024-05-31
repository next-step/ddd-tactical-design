package kitchenpos.tobe.product.domain.vo

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import kitchenpos.tobe.product.exception.product.ProductPriceException
import java.math.BigDecimal

@Embeddable
data class ProductPrice(
    @Column(name = "price", nullable = false)
    val price: BigDecimal,
) {
    companion object {
        fun of(price: BigDecimal): ProductPrice {
            validate(price)
            return ProductPrice(price)
        }

        private fun validate(price: BigDecimal) {
            require(price >= BigDecimal.ZERO) { throw ProductPriceException("상품 가격은 0원 이상이어야 합니다.") }
        }
    }

    fun changePrice(price: BigDecimal): ProductPrice {
        validate(price)
        return this.copy(price = price)
    }
}
