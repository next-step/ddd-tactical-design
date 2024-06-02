package kitchenpos.tobe.menu.domain

import kitchenpos.tobe.menu.domain.dto.ProductPrice
import java.util.*

interface ProductClient {
    fun getProductPrices(productIds: List<UUID>): List<ProductPrice>
}
