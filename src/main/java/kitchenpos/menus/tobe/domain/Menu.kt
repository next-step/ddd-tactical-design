package kitchenpos.menus.tobe.domain

import jakarta.persistence.*
import kitchenpos.common.Price
import java.util.*

@Table(name = "menu")
@Entity
class Menu(
    menuGroup: MenuGroup,
    name: String,
    price: Price,
    displayStatus: Boolean,
    menuProducts: MenuProducts,
    menuNameValidatorService: MenuNameValidatorService,
    menuPriceValidatorService: MenuPriceValidatorService
) {
    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    val id: UUID = UUID.randomUUID()

    @ManyToOne(optional = false)
    @JoinColumn(
        name = "menu_group_id",
        columnDefinition = "binary(16)",
        foreignKey = ForeignKey(name = "fk_menu_to_menu_group")
    )
    val menuGroup: MenuGroup = menuGroup

    @Column(name = "name", nullable = false)
    val name: String = name

    @Column(name = "price", nullable = false)
    var price: Price = price
        private set

    @Column(name = "displayed", nullable = false)
    var displayStatus: Boolean = displayStatus
        private set

    @OneToMany(cascade = [CascadeType.PERSIST, CascadeType.MERGE], mappedBy = "menu", fetch = FetchType.LAZY)
    val menuProducts: List<MenuProduct> = menuProducts.items

    init {
        this.menuProducts.forEach { it.menu = this }

        menuNameValidatorService.validate(name)
        menuPriceValidatorService.validate(this)
    }

    fun activateDisplayStatus(menuPriceValidatorService: MenuPriceValidatorService) {
        menuPriceValidatorService.validate(this)

        this.displayStatus = true
    }

    fun inActivateDisplayStatus() {
        this.displayStatus = false
    }

    fun changePrice(price: Price, menuPriceValidatorService: MenuPriceValidatorService) {
        this.price = price

        menuPriceValidatorService.validate(this)
    }
}
