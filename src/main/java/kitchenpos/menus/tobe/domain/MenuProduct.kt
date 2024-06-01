package kitchenpos.menus.tobe.domain

import jakarta.persistence.*
import java.util.*

@Table(name = "menu_product")
@Entity
class MenuProduct(
    @Column(name = "product_id", nullable = false)
    val productId: UUID,

    @Column(name = "quantity", nullable = false)
    val quantity: MenuProductQuantity,
) {
    @ManyToOne
    @JoinColumn(
        name = "menu_id",
        nullable = false,
        columnDefinition = "binary(16)",
        foreignKey = ForeignKey(name = "fk_menu_product_to_menu")
    )
    var menu: Menu? = null

    @Column(name = "seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    val seq: Long = 0L

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MenuProduct

        return seq == other.seq
    }

    override fun hashCode(): Int {
        return seq.hashCode()
    }

}
