package kitchenpos.product.tobe.application.dto

import kitchenpos.product.tobe.domain.Product
import java.math.BigDecimal
import java.util.*

data class ProductResp(
    val id: UUID,
    val name: String,
    val price: BigDecimal,
) {
    companion object {
        fun of(entity: Product): ProductResp {
            return ProductResp(
                id = entity.id!!,
                name = entity.productName.name,
                price = entity.price
            )
        }
    }
}
