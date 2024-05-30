package kitchenpos.menus.tobe.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.persistence.Transient
import java.util.UUID

private const val MIN_QUANTITY = 1L

@Table(name = "menu_product")
@Entity
class MenuProduct(
    quantity: Int,
    productId: UUID,
    productPrice: Int,
) {
    @Column(name = "seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    var seq: Long? = null

    @Column(name = "quantity", nullable = false)
    var quantity: Int = quantity

    @Transient
    var productId: UUID? = productId

    val productPrice: Int = productPrice

    init {
        require(MIN_QUANTITY <= quantity) { "메뉴 상품의 수량은 ${MIN_QUANTITY}개 이상이어야 합니다." }
    }

    fun amount(): Amount {
        return Amount.valueOf(productPrice * quantity)
    }
}
