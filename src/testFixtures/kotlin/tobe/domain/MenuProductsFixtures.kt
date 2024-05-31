package tobe.domain

import kitchenpos.tobe.menu.domain.vo.MenuProductV2
import java.util.*

object MenuProductsFixtures {
    fun createMenuProduct(productId: UUID = UUID.randomUUID()): MenuProductV2 {
        return MenuProductV2.of(
            price = 16000.toBigDecimal(),
            quantity = 1,
            productId = productId,
        )
    }
}
