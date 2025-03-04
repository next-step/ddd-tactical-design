package kitchenpos.menu.tobe.infra

import java.util.*
import kitchenpos.menu.tobe.domain.MenuGroup
import kitchenpos.menu.tobe.domain.MenuGroupRepository
import org.springframework.data.jpa.repository.JpaRepository

interface TobeJpaMenuGroupRepository : MenuGroupRepository, JpaRepository<MenuGroup, UUID> {

}
