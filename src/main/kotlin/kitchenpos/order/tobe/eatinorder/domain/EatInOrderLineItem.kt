package kitchenpos.order.tobe.eatinorder.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.*
import kitchenpos.menu.tobe.domain.MenuDisplay
import kitchenpos.order.tobe.common.OrderMenuInfo

@Table(name = "order_line_item")
@Entity(name = "TobeOrderLineItem")
class EatInOrderLineItem(
    @Column(name = "seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    var seq: Long,

    @Column(name = "quantity", nullable = false)
    var quantity: Long,

    @Column(name = "menu_id")
    var menuId: UUID,
) {
    companion object {
        fun create(
            seq: Long,
            menuInfo: OrderMenuInfo,
            quantity: Long,
        ): EatInOrderLineItem {
            check(menuInfo.menuDisplay == MenuDisplay.DISPLAYED) { "노출한 메뉴만 주문할 수 있습니다." }
            return EatInOrderLineItem(
                seq = seq,
                quantity = quantity,
                menuId = menuInfo.menuId,
            )
        }
    }
}
