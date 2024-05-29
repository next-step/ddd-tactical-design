package kitchenpos.menus.tobe.domain

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*

interface JpaMenuRepository : JpaRepository<Menu, UUID>, MenuRepository {
    @Query("select m from Menu m join m.menuProducts mp where mp.productId = :productId")
    override fun findAllByProductId(productId: UUID): List<Menu>
}
