package kitchenpos.tobe.menu.domain.entity

import jakarta.persistence.Column
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.ForeignKey
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import kitchenpos.tobe.menu.domain.MenuPurgomalumClient
import kitchenpos.tobe.menu.domain.vo.MenuName
import kitchenpos.tobe.menu.domain.vo.MenuPrice
import kitchenpos.tobe.menu.domain.vo.MenuProductV2
import kitchenpos.tobe.menu.domain.vo.MenuProducts
import java.math.BigDecimal
import java.util.*

@Table(name = "menu")
@Entity
class MenuV2 private constructor(
    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    val id: UUID,
    @Embedded
    private val name: MenuName,
    @Embedded
    private var price: MenuPrice,
    @Column(name = "displayed", nullable = false)
    var displayed: Boolean,
    @ManyToOne(optional = false)
    @JoinColumn(
        name = "menu_group_id",
        columnDefinition = "binary(16)",
        foreignKey = ForeignKey(name = "fk_menu_to_menu_group"),
    )
    val menuGroup: MenuGroupV2,
    @Embedded
    val menuProducts: MenuProducts,
) {
    companion object {
        fun of(
            name: String,
            price: BigDecimal,
            displayed: Boolean,
            menuGroup: MenuGroupV2,
            menuProducts: MutableList<MenuProductV2>,
            menuPurgomalumClient: MenuPurgomalumClient,
        ): MenuV2 {
            val menuPrice =
                MenuPrice.of(
                    price = price,
                    productsPrice = menuProducts.sumOf { it.price * it.quantity.toBigDecimal() },
                )

            return MenuV2(
                id = UUID.randomUUID(),
                name = MenuName.of(name, menuPurgomalumClient),
                price = menuPrice,
                displayed = displayed,
                menuGroup = menuGroup,
                menuProducts = MenuProducts(menuProducts),
            )
        }
    }

    fun handleProductPriceChanged(
        productId: UUID,
        price: BigDecimal,
    ) {
        menuProducts.handleProductPriceChanged(productId, price)

        if (getPrice() > getMenuProductsPriceSum()) {
            hide()
        }
    }

    private fun getMenuProductsPriceSum(): BigDecimal {
        return menuProducts.getPriceSum()
    }

    fun getPrice(): BigDecimal {
        return price.price
    }

    fun getName(): String {
        return name.name
    }

    fun getMenuProducts(): List<MenuProductV2> {
        return menuProducts.menuProducts
    }

    fun hide() {
        displayed = false
    }

    fun changeMenuPrice(price: BigDecimal) {
        this.price =
            MenuPrice.of(
                price = price,
                productsPrice = getMenuProductsPriceSum(),
            )
    }

    fun display() {
        displayed = true
    }
}
