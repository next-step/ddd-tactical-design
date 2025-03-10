package kitchenpos.menu.tobe.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.*

@Table(name = "menu_group")
@Entity(name = "TobeMenuGroup")
class MenuGroup(
    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    var id: UUID = UUID.randomUUID(),

    @Column(name = "name", nullable = false)
    val name: String,
) {
    init {
        require(name.isNotBlank()) { "메뉴 그룹 이름은 필수로 입력해야 합니다." }
    }
}
