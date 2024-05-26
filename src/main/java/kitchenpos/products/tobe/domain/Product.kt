package kitchenpos.products.tobe.domain

import jakarta.persistence.AttributeOverride
import jakarta.persistence.AttributeOverrides
import jakarta.persistence.Column
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.UUID

@Table(name = "product")
@Entity
class Product(
    name: ProductName,
    price: ProductPrice,
) {
    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private val id: UUID = UUID.randomUUID()

    @Embedded
    @AttributeOverrides(
        AttributeOverride(name = "value", column = Column(name = "name", nullable = false)),
    )
    var name: ProductName = name
        private set

    @Embedded
    @AttributeOverrides(
        AttributeOverride(name = "value", column = Column(name = "price", nullable = false)),
    )
    var price: ProductPrice = price
        private set

    fun changePrice(price: ProductPrice) {
        this.price = price
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Product) return false

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}
