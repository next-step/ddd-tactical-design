package kitchenpos.menu.tobe.domain

import jakarta.persistence.Column
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.ForeignKey
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.math.BigDecimal
import java.util.*
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes

@Table(name = "menu")
@Entity(name = "TobeMenu")
class Menu(
    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    var id: UUID? = null,

    @Embedded
    val menuName: MenuName,

    @Embedded
    var menuPrice: MenuPrice,

    @ManyToOne(optional = false)
    @JoinColumn(
        name = "menu_group_id",
        columnDefinition = "binary(16)",
        foreignKey = ForeignKey(name = "fk_menu_to_menu_group")
    )
    val menuGroup: MenuGroup,

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    @JdbcTypeCode(value = SqlTypes.VARCHAR)
    var menuDisplay: MenuDisplay,

    @Embedded
    val menuProducts: MenuProducts,
) {

    fun amount(menuAmountService: MenuAmountService): BigDecimal {
        return menuProducts.amount(menuAmountService)
    }

    fun changePrice(menuAmountService: MenuAmountService, menuPrice: MenuPrice) {
        if (menuPrice.price > menuProducts.amount(menuAmountService)) {
            throw IllegalArgumentException("메뉴가격은 메뉴금액 이하여야 합니다.")
        }
        this.menuPrice = menuPrice
    }

    fun display(menuAmountService: MenuAmountService) {
        if (menuPrice.price > menuProducts.amount(menuAmountService)) {
            throw IllegalArgumentException("메뉴가격은 메뉴금액 이하여야 합니다.")
        }
        menuDisplay = MenuDisplay.DISPLAYED
    }

    fun notDisplay() {
        menuDisplay = MenuDisplay.NOT_DISPLAYED
    }
}
