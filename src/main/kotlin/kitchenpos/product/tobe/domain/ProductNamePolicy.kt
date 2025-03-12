package kitchenpos.product.tobe.domain

import kitchenpos.common.annotation.DomainService
import kitchenpos.common.domain.Profanities

@DomainService
class ProductNamePolicy(private val profanities: Profanities) {
    fun validate(name: String) {
        require(name.isNotBlank()) { "상품 이름은 필수값입니다." }
        require(!profanities.contains(name)) { "상품 이름에 금지어가 포함되어 있습니다." }
    }
}
