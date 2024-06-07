package kitchenpos.menus.tobe.domain

import jakarta.persistence.*
import kitchenpos.common.Price
import java.util.*

@Table(name = "menu")
@Entity
class Menu private constructor(
    menuGroup: MenuGroup,
    name: MenuName,
    price: Price,
    displayStatus: Boolean,
    menuProducts: MenuProducts
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
    val name: MenuName = name

    @Column(name = "price", nullable = false)
    var price: Price = price
        private set

    @Column(name = "displayed", nullable = false)
    var displayStatus: Boolean = displayStatus
        private set

    @Embedded
    val menuProducts: MenuProducts = menuProducts

    private val isMenuPriceValid: Boolean
        get() = price <= menuProducts.totalPrice

    companion object {
        fun of(
            menuGroup: MenuGroup,
            name: MenuName,
            price: Price,
            displayStatus: Boolean,
            menuProducts: MenuProducts,
        ): Menu = Menu(
            menuGroup = menuGroup,
            name = name,
            price = price,
            displayStatus = displayStatus,
            menuProducts = menuProducts
        )
    }

    init {
        validateMenuPrice()
        this.menuProducts.apply(this)
    }

    fun activateDisplayStatus() {
        this.displayStatus = true

        validateMenuPrice()
    }

    fun inActivateDisplayStatus() {
        this.displayStatus = false
    }

    fun changePrice(price: Price) {
        this.price = price

        validateMenuPrice()
    }

    fun changeMenuProduct(productId: UUID, productPrice: Price) {
        menuProducts.changeMenuProduct(productId, productPrice)

        if (!isMenuPriceValid) {
            inActivateDisplayStatus()
        }
    }

    private fun validateMenuPrice() {
        if (displayStatus && !isMenuPriceValid) {
            throw IllegalArgumentException("메뉴의 가격이 메뉴상품 가격의 총합보다 클 수 없습니다")
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Menu

        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

}
