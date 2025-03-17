package kitchenpos.order.tobe.common

import java.util.UUID

interface OrderMenuClient {
    fun getMenu(menuId: UUID): OrderMenuInfo
}
