package kitchenpos.tobe.product.domain.entity

import jakarta.persistence.Column
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import kitchenpos.tobe.product.domain.ProductPurgomalumClient
import kitchenpos.tobe.product.domain.vo.ProductName
import kitchenpos.tobe.product.domain.vo.ProductPrice
import java.math.BigDecimal
import java.util.*

@Table(name = "product")
@Entity
class ProductV2 private constructor(
    @Id
    @Column(name = "id", columnDefinition = "binary(16)", nullable = false)
    val id: UUID,
    @Embedded
    val name: ProductName,
    @Embedded
    var price: ProductPrice,
) {
    companion object {
        fun of(
            name: String,
            price: BigDecimal,
            purgomalumClient: ProductPurgomalumClient,
        ): ProductV2 {
            return ProductV2(
                UUID.randomUUID(),
                ProductName.of(name, purgomalumClient),
                ProductPrice.of(price),
            )
        }
    }

    fun changePrice(price: BigDecimal): ProductV2 {
        this.price = this.price.changePrice(price)
        return this
    }

    fun getName(): String {
        return name.name
    }

    fun getPrice(): BigDecimal {
        return price.price
    }
}
