package kitchenpos.tobe.menu.domain.entity

import jakarta.persistence.CascadeType
import jakarta.persistence.Embeddable
import jakarta.persistence.ForeignKey
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToMany
import java.math.BigDecimal
import java.util.*

@Embeddable
class MenuProducts(
    @OneToMany(cascade = [CascadeType.PERSIST, CascadeType.MERGE])
    @JoinColumn(
        name = "menu_id",
        nullable = false,
        columnDefinition = "binary(16)",
        foreignKey = ForeignKey(name = "fk_menu_product_to_menu"),
    )
    val menuProducts: MutableList<MenuProductV2>,
) {
    fun changeProductPrice(
        productId: UUID,
        price: BigDecimal,
    ) {
        menuProducts.find {
            it.productId == productId
        }?.changePrice(price) ?: throw IllegalArgumentException()
    }

    fun getPriceSum(): BigDecimal {
        return menuProducts.sumOf {
            it.price
        }
    }
}
