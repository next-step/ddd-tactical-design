package kitchenpos.tobe.menu.domain

import kitchenpos.tobe.menu.domain.dto.ProductPrice
import java.util.*

interface ProductQueryClient {
    fun getProductPrices(productIds: List<UUID>): List<ProductPrice>
}
