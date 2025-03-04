package kitchenpos.product.tobe.domain

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import java.math.BigDecimal

@Embeddable
class ProductPrice(

    productPricePolicy: ProductPricePolicy,

    @Column(name = "price", nullable = false)
    val price: BigDecimal

) : Comparable<ProductPrice> {

    init {
        productPricePolicy.validate(price)
    }

    override fun compareTo(other: ProductPrice): Int {
        return price.compareTo(other.price)
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
