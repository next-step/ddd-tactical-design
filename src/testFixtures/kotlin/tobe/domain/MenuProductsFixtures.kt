package tobe.domain

import kitchenpos.tobe.menu.domain.vo.MenuProductV2
import kitchenpos.tobe.product.domain.entity.ProductV2
import java.util.*

object MenuProductsFixtures {
    fun createMenuProduct(product: ProductV2? = null): MenuProductV2 {
        return MenuProductV2.from(
            price = product?.getPrice() ?: 16000.toBigDecimal(),
            quantity = 1,
            productId = product?.id ?: UUID.randomUUID(),
        )
    }
}
