package kitchenpos.tobe.menu.domain.vo

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import java.math.BigDecimal

@Embeddable
class MenuPrice private constructor(
    @Column(name = "price", nullable = false)
    val price: BigDecimal,
) {
    companion object {
        fun from(
            price: BigDecimal,
            productsPrice: BigDecimal,
        ): MenuPrice {
            require(
                price <= productsPrice,
            ) {
                throw IllegalArgumentException("메뉴의 가격은 메뉴에 속한 상품의 가격 합보다 작거나 같아야 합니다.")
            }
            return MenuPrice(price)
        }
    }
}
