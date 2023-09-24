package kitchenpos.menus.infra;

import kitchenpos.menus.domain.menugroup.MenuGroup;
import kitchenpos.menus.domain.menugroup.MenuGroupRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaMenuGroupRepository extends MenuGroupRepository, JpaRepository<MenuGroup, UUID> {
}
