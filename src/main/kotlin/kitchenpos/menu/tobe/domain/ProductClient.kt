package kitchenpos.menu.tobe.domain

import java.util.*

interface ProductClient {
    fun getProducts(productIds: List<UUID>): Map<UUID, ProductInfo>
}
