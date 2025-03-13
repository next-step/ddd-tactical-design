package kitchenpos.menu.tobe.domain

import java.util.*
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Repository

@Repository("tobeMenuGroupRepository")
@Primary
interface MenuGroupRepository {
    fun save(menuGroup: MenuGroup): MenuGroup

    fun findById(id: UUID): Optional<MenuGroup>

    fun findAll(): List<MenuGroup>
}
