package kitchenpos.menus.tobe.infra

import kitchenpos.menus.tobe.domain.Menu
import kitchenpos.menus.tobe.domain.MenuRepository
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface JpaMenuRepository : MenuRepository, JpaRepository<Menu, UUID>
