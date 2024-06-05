package kitchenpos.menus.tobe.domain

import jakarta.persistence.CascadeType
import jakarta.persistence.Embeddable
import jakarta.persistence.FetchType
import jakarta.persistence.OneToMany
import kitchenpos.common.Price
import kitchenpos.common.ZERO
import java.util.UUID

@Embeddable
class MenuProducts(
    @OneToMany(cascade = [CascadeType.PERSIST, CascadeType.MERGE], mappedBy = "menu", fetch = FetchType.LAZY)
    val items: List<MenuProduct>
) {
    val totalPrice: Price
        get() = items.map { it.price }.foldRight(ZERO) { acc, price -> acc + price }

    init {
        require(items.isNotEmpty()) { "적어도 1개 이상의 상품을 등록해야합니다" }
    }

    //연관관계 편의 메소드
    fun apply(menu: Menu) {
        items.forEach { it.menu = menu }
    }

    val productIds: List<UUID>
        get() = items.map { it.productId }
}
