package kitchenpos.menus.tobe.domain

import jakarta.persistence.AttributeOverride
import jakarta.persistence.AttributeOverrides
import jakarta.persistence.Column
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.ForeignKey
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.util.UUID

@Table(name = "menu")
@Entity
class Menu(
    name: MenuName,
    price: Amount,
    menuGroup: MenuGroup,
    menuProducts: MenuProducts,
    displayed: Boolean = false,
) {
    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    var id: UUID = UUID.randomUUID()

    @Embedded
    @AttributeOverrides(
        AttributeOverride(name = "value", column = Column(name = "name", nullable = false)),
    )
    var name: MenuName = name

    @Embedded
    @AttributeOverrides(
        AttributeOverride(name = "value", column = Column(name = "price", nullable = false)),
    )
    var price: Amount = price

    @ManyToOne(optional = false)
    @JoinColumn(
        name = "menu_group_id",
        columnDefinition = "binary(16)",
        foreignKey = ForeignKey(name = "fk_menu_to_menu_group"),
    )
    var menuGroup: MenuGroup = menuGroup

    @Column(name = "displayed", nullable = false)
    var isDisplayed: Boolean = displayed

    @Embedded
    var menuProducts: MenuProducts = menuProducts

    init {
        validatePrice(price, menuProducts)
    }

    fun changePrice(price: Amount) {
        validatePrice(price, menuProducts)
        this.price = price
    }

    fun display() {
        validatePrice(price, menuProducts)
        isDisplayed = true
    }

    fun hide() {
        isDisplayed = false
    }

    private fun validatePrice(
        price: Amount,
        menuProducts: MenuProducts,
    ) {
        require(price >= menuProducts.totalAmount()) { "메뉴의 가격은 메뉴 상품의 가격보다 크거나 같아야 합니다." }
    }
}
