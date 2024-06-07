package kitchenpos.products.tobe.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import kitchenpos.common.Price
import java.util.*

@Table(name = "product")
@Entity
class Product(name: ProductName, price: Price) {
    @Id
    @Column(name = "id", columnDefinition = "binary(16)")
    val id: UUID = UUID.randomUUID()

    @Column(name = "name", nullable = false)
    val name: ProductName = name

    @Column(name = "price", nullable = false)
    var price: Price = price
        private set

    fun changePrice(price: Price) {
        this.price = price
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Product

        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

}
