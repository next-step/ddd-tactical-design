package kitchenpos.product.tobe.domain

import jakarta.persistence.Column
import jakarta.persistence.Embeddable

@Embeddable
class ProductName(
    productNamePolicy: ProductNamePolicy,

    @Column(name = "name", nullable = false)
    val name: String
) {
    init {
        productNamePolicy.validate(name)
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
