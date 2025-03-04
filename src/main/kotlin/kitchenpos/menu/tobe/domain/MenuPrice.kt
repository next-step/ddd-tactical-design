package kitchenpos.menu.tobe.domain

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import java.math.BigDecimal

@Embeddable
class MenuPrice(
    @Column(name = "price", nullable = false)
    val price: BigDecimal
) {

    init {
        require(price >= BigDecimal.ZERO) { "메뉴 가격은 0 이상이어야 합니다." }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MenuPrice

        return price == other.price
    }

    override fun hashCode(): Int {
        return price.hashCode()
    }
}
