package kitchenpos.menus.tobe.domain

import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface JpaMenuGroupRepository : JpaRepository<MenuGroup, UUID>, MenuGroupRepository {
}
