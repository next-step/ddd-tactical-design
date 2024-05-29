package kitchenpos.tobe.eatinorder.domain.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.math.BigDecimal
import java.util.*

@Entity
@Table(name = "order_line_item")
class OrderLineItemV2(
    @Column(name = "seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    val seq: Long? = null,
    @Column(name = "menu_id", columnDefinition = "binary(16)")
    val menuId: UUID,
    @Column(name = "quantity", nullable = false)
    var quantity: Int,
    @Column(name = "price", nullable = false)
    var price: BigDecimal,
)
