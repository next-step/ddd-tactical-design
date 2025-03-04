package kitchenpos.menu.tobe.domain

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
import kitchenpos.menu.domain.MenuGroup

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

    @Column(name = "displayed", nullable = false)
    var displayed: Boolean,

    @Embedded
    val menuProducts: MenuProducts,
) {

    fun amount(): BigDecimal {
        return menuProducts.amount()
    }
}
