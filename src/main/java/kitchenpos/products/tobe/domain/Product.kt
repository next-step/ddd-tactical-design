package kitchenpos.products.tobe.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.*

@Table(name = "product")
@Entity
class Product(
    @Column(name = "displayed_name", nullable = false)
    val displayedName: String,
    price: ProductPrice
) {
    @Id
    @Column(name = "id", columnDefinition = "binary(16)")
    val id: UUID = UUID.randomUUID()

    @Column(name = "price", nullable = false)
    var price: ProductPrice = price
        private set

    fun changePrice(price: ProductPrice) {
        this.price = price
    }
}
