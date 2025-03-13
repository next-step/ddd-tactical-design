package kitchenpos.menu.tobe.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.*

@Table(name = "menu_product")
@Entity(name = "TobeMenuProduct")
class MenuProduct(
    @Column(name = "seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    var seq: Long? = null,

    @Column(name = "product_id")
    var productId: UUID,

    @Column(name = "quantity", nullable = false)
    val quantity: Long
) {

    init {
        require(quantity >= 0) { "메뉴 상품의 수량은 0개 이상이어야 합니다." }
    }
}
