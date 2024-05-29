package kitchenpos.menus.tobe.domain

import jakarta.persistence.AttributeOverride
import jakarta.persistence.AttributeOverrides
import jakarta.persistence.Column
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.UUID

@Table(name = "menu_group")
@Entity
class MenuGroup(
    name: String,
) {
    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    val id: UUID = UUID.randomUUID()

    @Embedded
    @AttributeOverrides(
        AttributeOverride(name = "value", column = Column(name = "name", nullable = false)),
    )
    val name: String = name

    init {
        require(name.isNotEmpty()) { "메뉴 그룹 이름은 비어있을 수 없습니다." }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is MenuGroup) return false

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}
