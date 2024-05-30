package kitchenpos.menus.tobe.domain

import jakarta.persistence.CascadeType
import jakarta.persistence.Embeddable
import jakarta.persistence.FetchType
import jakarta.persistence.OneToMany

@Embeddable
class MenuProducts(
    @OneToMany(mappedBy = "menu", fetch = FetchType.LAZY, cascade = [CascadeType.ALL], orphanRemoval = true)
    private val menuProducts: MutableList<MenuProduct>,
) {
    init {
        require(menuProducts.isNotEmpty()) { "메뉴 상품은 비어있을 수 없습니다." }
    }

    fun add(menuProduct: MenuProduct) {
        menuProducts.add(menuProduct)
    }

    fun totalAmount(): Amount {
        return menuProducts.map { it.amount() }.reduce { acc, amount -> acc + amount }
    }
}
