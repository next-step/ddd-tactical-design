package kitchenpos.menus.tobe.infra

import kitchenpos.menus.tobe.domain.MenuGroup
import kitchenpos.menus.tobe.domain.MenuGroupRepository
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface JpaMenuGroupRepository : MenuGroupRepository, JpaRepository<MenuGroup, UUID>
