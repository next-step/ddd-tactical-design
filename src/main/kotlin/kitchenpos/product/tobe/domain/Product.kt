package kitchenpos.product.tobe.domain

import jakarta.persistence.Column
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.*

@Table(name = "product")
@Entity(name = "TobeProduct")
class Product(
    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    var id: UUID? = null,

    @Embedded
    var productName: ProductName,

    @Embedded
    var productPrice: ProductPrice
) {
    fun changePrice(productPrice: ProductPrice) {
        this.productPrice = productPrice
    }
}
