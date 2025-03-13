package kitchenpos.product.tobe.domain

import jakarta.persistence.Column
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.*
import org.springframework.data.domain.AbstractAggregateRoot

@Table(name = "product")
@Entity(name = "TobeProduct")
class Product(
    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    var id: UUID = UUID.randomUUID(),

    @Embedded
    val productName: ProductName,

    @Embedded
    var productPrice: ProductPrice
) : AbstractAggregateRoot<Product>() {
    fun changePrice(productPrice: ProductPrice) {
        this.productPrice = productPrice
        registerEvent(ProductPriceChangedEvent(id))
    }
}
