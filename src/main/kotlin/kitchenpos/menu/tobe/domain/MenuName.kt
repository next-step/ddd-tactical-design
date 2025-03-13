package kitchenpos.menu.tobe.domain

import jakarta.persistence.Column
import jakarta.persistence.Embeddable

@Embeddable
class MenuName(
    menuNamePolicy: MenuNamePolicy,

    @Column(name = "name", nullable = false)
    val name: String
) {
    init {
        menuNamePolicy.validate(name)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MenuName

        return name == other.name
    }

    override fun hashCode(): Int {
        return name.hashCode()
    }
}
