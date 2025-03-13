package kitchenpos.menu.tobe.domain

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
        foreignKey = ForeignKey(name = "fk_menu_product_to_menu")
    )
    val menuProducts: List<MenuProduct>
) {
    init {
        require(menuProducts.isNotEmpty()) { "메뉴 상품은 필수로 입력해야 합니다." }
    }

    fun amount(productPrices: Map<UUID, ProductInfo>): BigDecimal {
        var sum = BigDecimal.ZERO
        menuProducts.forEach { menuProduct ->
            val productInfo = productPrices[menuProduct.productId]
                ?: throw NoSuchElementException("상품을 찾을 수 없습니다.")
            sum += productInfo.price * menuProduct.quantity.toBigDecimal()
        }
        return sum
    }

    fun productIds(): List<UUID> {
        return menuProducts.map { it.productId }
    }
}
