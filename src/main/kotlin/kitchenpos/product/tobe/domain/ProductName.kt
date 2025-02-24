package kitchenpos.product.tobe.domain

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import kitchenpos.common.domain.Profanities

@Embeddable
class ProductName(
    profanities: Profanities,

    @Column(name = "name", nullable = false)
    val name: String
) {
    init {
        require(name.isNotBlank()) { "상품 이름은 필수값입니다." }
        require(!profanities.contains(name)) { "상품 이름에 금지어가 포함되어 있습니다." }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ProductName

        return name == other.name
    }

    override fun hashCode(): Int {
        return name.hashCode()
    }


}
