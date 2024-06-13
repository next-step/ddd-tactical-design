package kitchenpos.menus.tobe.infra.repository;

import java.util.UUID;
import kitchenpos.menus.tobe.domain.entity.MenuGroup;
import org.springframework.data.jpa.repository.JpaRepository;

interface JpaMenuGroupRepository extends JpaRepository<MenuGroup, UUID> {
}
