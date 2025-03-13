package kitchenpos.menu.tobe.infra

import java.util.*
import kitchenpos.menu.tobe.domain.Menu
import kitchenpos.menu.tobe.domain.MenuRepository
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface TobeJpaMenuRepository : MenuRepository, JpaRepository<Menu, UUID> {

    @Query("select m from TobeMenu m join m.menuProducts.menuProducts mp where mp.productId = :productId")
    override fun findAllByProductId(@Param("productId") productId: UUID): List<Menu>
}
