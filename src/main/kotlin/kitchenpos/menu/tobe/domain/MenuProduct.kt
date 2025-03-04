package kitchenpos.menu.tobe.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.ForeignKey
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import kitchenpos.product.tobe.domain.Product

@Table(name = "menu_product")
@Entity(name = "TobeMenuProduct")
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
    val quantity: Long
) {

    init {
        require(quantity >= 0) { "메뉴 상품의 수량은 0개 이상이어야 합니다." }
    }
}
