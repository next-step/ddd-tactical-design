package kitchenpos.eatinorders.tobe.domain

import jakarta.persistence.*
import kitchenpos.common.Price
import java.util.*

@Table(name = "eat_in_order_line_items")
@Entity
class EatInOrderLineItem(
    menuId: UUID,
    quantity: EatInOrderLineItemQuantity,
    price: Price
) {
    @Column(name = "seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    val seq: Long = 0L

    @Column(name = "menu_id", columnDefinition = "binary(16)", nullable = false)
    val menuId: UUID = menuId

    @Column(name = "quantity", nullable = false)
    val quantity: EatInOrderLineItemQuantity = quantity

    @Column(name = "price", nullable = false)
    val price: Price = price

    @ManyToOne
    @JoinColumn(name = "eat_in_order_id", nullable = false, columnDefinition = "binary(16)")
    var eatInOrder: EatInOrder? = null

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as EatInOrderLineItem

        return seq == other.seq
    }

    override fun hashCode(): Int {
        return seq.hashCode()
    }

}
