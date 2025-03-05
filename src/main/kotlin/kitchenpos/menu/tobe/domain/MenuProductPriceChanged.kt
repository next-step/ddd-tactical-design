package kitchenpos.menu.tobe.domain

import java.util.*

/**
 * Product 관점
 * - ProductPrice를 변경될 때, 해당 Product를 사용하는 Menu의 MenuPrice>MenuAmount이면 Menu를 NotDisplayed한다.
 * Menu 관점
 * - Menu에 속한 MenuProduct의 ProductPrice가 변경될 때, MenuPrice>MenuAmount이면 NotDisplayed한다.
 *
 * Menu관점이 더 자연스럽다고 생각하고 추가된 도메인서비스
 */

interface MenuProductPriceChanged {
    fun changedProduct(productId: UUID)
}
