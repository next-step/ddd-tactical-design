package kitchenpos.menu.tobe.domain

import java.math.BigDecimal
import java.util.*

interface ProductClient {
    fun getProductPrice(productId: UUID): BigDecimal
}
