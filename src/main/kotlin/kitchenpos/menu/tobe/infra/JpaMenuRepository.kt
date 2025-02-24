package kitchenpos.menu.tobe.infra

import kitchenpos.menu.tobe.domain.Menu
import kitchenpos.menu.tobe.domain.MenuRepository
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.*

interface JpaMenuRepository: MenuRepository, JpaRepository<Menu, UUID> {

    @Query("select m from Menu m join m.menuProducts mp where mp.product.id = :productId")
    override fun findAllByProductId(@Param("productId") productId: UUID): List<Menu>
}
