package kitchenpos.tobe.menu.domain.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.*

@Entity
@Table(name = "menu_group")
class MenuGroupV2(
    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    val id: UUID,
    @Column(name = "name", nullable = false)
    val name: String,
) {
    companion object {
        fun of(name: String): MenuGroupV2 {
            return MenuGroupV2(
                id = UUID.randomUUID(),
                name = name,
            )
        }
    }
}
