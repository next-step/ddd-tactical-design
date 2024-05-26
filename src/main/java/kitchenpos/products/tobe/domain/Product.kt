package kitchenpos.products.tobe.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.*

@Table(name = "product")
@Entity
class Product(
    displayedName: String,
    price: ProductPrice,
    productNameValidatorService: ProductNameValidatorService
) {
    @Id
    @Column(name = "id", columnDefinition = "binary(16)")
    val id: UUID = UUID.randomUUID()

    @Column(name = "displayed_name", nullable = false)
    val displayedName: String = displayedName

    @Column(name = "price", nullable = false)
    var price: ProductPrice = price
        private set

    init {
        productNameValidatorService.validate(displayedName)
    }

    fun changePrice(price: ProductPrice) {
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
