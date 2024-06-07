package kitchenpos.menus.tobe.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.*

@Table(name = "menu_group")
@Entity
class MenuGroup(
    @Column(name = "name", nullable = false)
    val name: MenuGroupName
) {
    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    val id: UUID = UUID.randomUUID()
}
