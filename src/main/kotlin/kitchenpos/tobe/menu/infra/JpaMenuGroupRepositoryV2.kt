package kitchenpos.tobe.menu.infra

import kitchenpos.tobe.menu.domain.entity.MenuGroupV2
import kitchenpos.tobe.menu.domain.repository.MenuGroupRepositoryV2
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface JpaMenuGroupRepositoryV2 : JpaRepository<MenuGroupV2, UUID>, MenuGroupRepositoryV2 {
    override fun findMenuGroupById(id: UUID): MenuGroupV2?
}
