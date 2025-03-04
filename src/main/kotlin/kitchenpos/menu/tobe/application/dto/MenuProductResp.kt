package kitchenpos.menu.tobe.application.dto

import java.util.*
import kitchenpos.menu.tobe.domain.MenuProduct

data class MenuProductResp(
    val seq: Long,
    val productId: UUID,
    val quantity: Long
) {
    companion object {
        fun of(menuProduct: MenuProduct): MenuProductResp {
            return MenuProductResp(
                seq = menuProduct.seq!!,
                productId = menuProduct.productId!!,
                quantity = menuProduct.quantity
            )
        }
    }
}
