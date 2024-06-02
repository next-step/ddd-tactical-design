package kitchenpos.tobe.menu.domain

import kitchenpos.tobe.menu.domain.repository.MenuRepositoryV2
import kitchenpos.tobe.support.DomainService
import java.math.BigDecimal
import java.util.*

@DomainService
class MenuDisplayChecker(
    private val menuRepositoryV2: MenuRepositoryV2,
) {
    /***
     * 상품의 가격이 변경됨으로써
     * 메뉴의 가격이 메뉴에 속한 상품 금액의 합보다 크면
     * 메뉴가 숨겨진다.
     */
    fun handleProductPriceChanged(
        productId: UUID,
        price: BigDecimal,
    ) {
        val menus = menuRepositoryV2.findAllByProductId(productId)

        menus.forEach { menu ->
            menu.handleProductPriceChanged(productId, price)
        }
    }
}
