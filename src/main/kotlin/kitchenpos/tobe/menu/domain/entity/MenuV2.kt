package kitchenpos.tobe.menu.domain.entity

import jakarta.persistence.Column
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.math.BigDecimal
import java.util.*

@Table(name = "menu")
@Entity
class MenuV2(
    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    val id: UUID,
    @Column(name = "name", nullable = false)
    val name: String,
    @Column(name = "price", nullable = false)
    val price: BigDecimal,
    @Column(name = "displayed", nullable = false)
    var displayed: Boolean,
    @Embedded
    val menuProducts: MenuProducts,
) {
    fun changeProductPrice(
        productId: UUID,
        price: BigDecimal,
    ) {
        menuProducts.changeProductPrice(productId, price)
        if (menuProducts.getPriceSum() > this.price) {
            this.displayed = false
        }
    }
}
