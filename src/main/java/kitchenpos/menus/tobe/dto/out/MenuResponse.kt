package kitchenpos.menus.tobe.dto.out

import kitchenpos.common.Price
import kitchenpos.menus.tobe.domain.Menu
import kitchenpos.menus.tobe.domain.MenuProduct
import java.util.*

data class MenuResponse(
    val id: UUID,
    val menuGroup: MenuGroupResponse,
    val name: String,
    val price: Price,
    val displayStatus: Boolean,
    val menuProducts: List<MenuProductResponse>
)

data class MenuProductResponse(
    val seq: Long,
    val productId: UUID,
    val quantity: Long
)


fun fromMenu(menu: Menu): MenuResponse = MenuResponse(
    id = menu.id,
    menuGroup = fromMenuGroup(menu.menuGroup),
    name = menu.name.value,
    price = menu.price,
    displayStatus = menu.displayStatus,
    menuProducts = menu.menuProducts.items.map { fromMenuProduct(it) }
)

fun fromMenuProduct(menuProduct: MenuProduct): MenuProductResponse = MenuProductResponse(
    seq = menuProduct.seq,
    productId = menuProduct.productId,
    quantity = menuProduct.quantity.value
)
