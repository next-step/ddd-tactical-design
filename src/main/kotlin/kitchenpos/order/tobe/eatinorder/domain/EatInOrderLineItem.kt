package kitchenpos.order.tobe.eatinorder.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.*
import kitchenpos.order.tobe.common.OrderMenuInfo

@Table(name = "order_line_item")
@Entity(name = "TobeOrderLineItem")
class EatInOrderLineItem(
    orderMenuInfo: OrderMenuInfo,

    @Column(name = "seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    var seq: Long,

    @Column(name = "quantity", nullable = false)
    var quantity: Long,

    @Column(name = "order_id")
    var menuId: UUID,
) {
    init {
        check(orderMenuInfo.isDisplay) { "주문 가능한 메뉴만 주문할 수 있습니다." }
    }
}
