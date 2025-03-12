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
import java.util.*
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes

@Table(name = "menu")
@Entity(name = "TobeMenu")
class Menu(
    productClient: ProductClient,

    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    var id: UUID = UUID.randomUUID(),

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
    @Column(name = "displayed", nullable = false)
    @JdbcTypeCode(value = SqlTypes.VARCHAR)
    var menuDisplay: MenuDisplay,

    @Embedded
    val menuProducts: MenuProducts,
) {

    init {
        validateMenuPrice(productClient, menuPrice)
    }

    fun changePrice(productClient: ProductClient, menuPrice: MenuPrice) {
        validateMenuPrice(productClient, menuPrice)
        this.menuPrice = menuPrice
    }

    fun display(productClient: ProductClient) {
        validateMenuPrice(productClient, menuPrice)
        menuDisplay = MenuDisplay.DISPLAYED
    }

    fun notDisplay() {
        menuDisplay = MenuDisplay.NOT_DISPLAYED
    }

    fun canDisplay(productClient: ProductClient): Boolean {
        return menuPrice.price <= menuProducts.amount(productClient)
    }

    private fun validateMenuPrice(productClient: ProductClient, menuPrice: MenuPrice) {
        if (menuPrice.price > menuProducts.amount(productClient)) {
            throw IllegalArgumentException("메뉴가격은 메뉴금액 이하여야 합니다.")
        }
    }
}
