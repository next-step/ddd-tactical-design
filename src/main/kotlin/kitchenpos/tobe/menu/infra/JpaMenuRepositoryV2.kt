package kitchenpos.tobe.menu.infra

import kitchenpos.tobe.menu.domain.entity.MenuV2
import kitchenpos.tobe.menu.domain.repository.MenuRepositoryV2
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*

interface JpaMenuRepositoryV2 : JpaRepository<MenuV2, UUID>, MenuRepositoryV2 {
    @Query("select m from Menu m join m.menuProducts mp where mp.product.id = :productId")
    override fun findAllByProductId(productId: UUID): List<MenuV2>
}
