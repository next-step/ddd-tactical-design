package kitchenpos.menu.tobe.domain

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.ForeignKey
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import jakarta.persistence.Transient
import java.math.BigDecimal
import java.util.*
import kitchenpos.menu.domain.MenuGroup

@Table(name = "menu")
@Entity
class Menu(
    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    var id: UUID? = null,

    @Column(name = "name", nullable = false)
    val name: String,

    @Column(name = "price", nullable = false)
    var price: BigDecimal,

    @ManyToOne(optional = false)
    @JoinColumn(
        name = "menu_group_id",
        columnDefinition = "binary(16)",
        foreignKey = ForeignKey(name = "fk_menu_to_menu_group")
    )
    val menuGroup: MenuGroup,

    @Column(name = "displayed", nullable = false)
    var displayed: Boolean,

    @OneToMany(cascade = [CascadeType.PERSIST, CascadeType.MERGE])
    @JoinColumn(
        name = "menu_id",
        nullable = false,
        columnDefinition = "binary(16)",
        foreignKey = ForeignKey(name = "fk_menu_product_to_menu")
    )
    val menuProducts: List<MenuProduct>,

    @Transient
    val menuGroupId: UUID,
) {

    fun amount(): BigDecimal {
        var sum = BigDecimal.ZERO
        for (menuProduct in menuProducts) {
            sum = sum.add(
                menuProduct.product
                    .price
                    .multiply(BigDecimal.valueOf(menuProduct.quantity))
            )
        }
        return sum
    }

}
