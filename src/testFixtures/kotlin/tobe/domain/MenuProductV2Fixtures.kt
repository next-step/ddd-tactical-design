package tobe.domain

import kitchenpos.tobe.menu.domain.vo.MenuProductV2
import kitchenpos.tobe.product.domain.entity.ProductV2
import java.util.*

object MenuProductV2Fixtures {
    fun createMenuProduct(product: ProductV2? = null): MenuProductV2 {
        return MenuProductV2.of(
            price = product?.getPrice() ?: 16000.toBigDecimal(),
            quantity = 1,
            productId = product?.id ?: UUID.randomUUID(),
        )
    }
}
