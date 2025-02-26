package kitchenpos.menu.tobe.domain

import java.util.*
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Repository

@Repository("tobeMenuRepository")
@Primary
interface MenuRepository {
    fun save(menu: Menu): Menu
    fun findById(id: UUID): Optional<Menu>
    fun findAll(): List<Menu>
    fun findAllByIdIn(ids: List<UUID>): List<Menu>
    fun findAllByProductId(productId: UUID): List<Menu>
}
