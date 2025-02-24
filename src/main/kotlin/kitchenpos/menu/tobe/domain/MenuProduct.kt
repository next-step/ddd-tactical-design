package kitchenpos.menu.tobe.domain

import jakarta.persistence.*
import kitchenpos.product.tobe.domain.Product
import java.util.*

@Table(name = "menu_product")
@Entity
class MenuProduct(
    @Column(name = "seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    var seq: Long? = null,

    @ManyToOne(optional = false)
    @JoinColumn(
        name = "product_id",
        columnDefinition = "binary(16)",
        foreignKey = ForeignKey(name = "fk_menu_product_to_product")
    )
    var product: Product,

    @Column(name = "quantity", nullable = false)
    val quantity: Long,

    @Transient
    val productId: UUID
) {
}
