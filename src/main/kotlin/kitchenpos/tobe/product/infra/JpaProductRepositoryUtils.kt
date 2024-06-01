package kitchenpos.tobe.product.infra

import kitchenpos.tobe.product.domain.entity.ProductV2
import kitchenpos.tobe.product.domain.repository.ProductRepositoryV2
import java.util.*

fun ProductRepositoryV2.getProductById(productId: UUID): ProductV2 {
    return this.findProductById(productId)
        ?: throw IllegalArgumentException("[$productId]존재하지 않는 상품입니다.")
}
