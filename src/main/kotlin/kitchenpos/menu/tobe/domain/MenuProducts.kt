package kitchenpos.menu.tobe.domain

import jakarta.persistence.CascadeType
import jakarta.persistence.Embeddable
import jakarta.persistence.ForeignKey
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToMany
import java.math.BigDecimal

@Embeddable
class MenuProducts(
    @OneToMany(cascade = [CascadeType.PERSIST, CascadeType.MERGE])
    @JoinColumn(
        name = "menu_id",
        nullable = false,
        columnDefinition = "binary(16)",
        foreignKey = ForeignKey(name = "fk_menu_product_to_menu")
    )
    val menuProducts: List<MenuProduct>
) {
    fun amount(): BigDecimal {
        var sum = BigDecimal.ZERO
        menuProducts.forEach { menuProduct ->
            sum = sum.add(
                menuProduct.product.productPrice.price
                    .multiply(BigDecimal.valueOf(menuProduct.quantity))
            )
        }
        return sum
    }
}
