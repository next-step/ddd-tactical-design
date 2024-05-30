package kitchenpos.tobe.menu.domain.entity

import jakarta.persistence.Column
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.ForeignKey
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.math.BigDecimal
import java.util.*

@Table(name = "menu")
@Entity
class MenuV2 private constructor(
    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    val id: UUID,
    @Column(name = "name", nullable = false)
    val name: String,
    @Column(name = "price", nullable = false)
    val price: BigDecimal,
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
        ): MenuV2 {
            return MenuV2(
                id = UUID.randomUUID(),
                name = name,
                price = price,
                displayed = displayed,
                menuGroup = menuGroup,
                menuProducts =
                    MenuProducts(
                        menuProducts = menuProducts,
                    ),
            )
        }
    }

    fun changeProductPrice(
        productId: UUID,
        price: BigDecimal,
    ) {
        menuProducts.changeProductPrice(productId, price)
        if (menuProducts.getPriceSum() <= this.price) {
            this.displayed = false
        }
    }

    fun getMenuProducts(): List<MenuProductV2> {
        return menuProducts.menuProducts
    }
}
