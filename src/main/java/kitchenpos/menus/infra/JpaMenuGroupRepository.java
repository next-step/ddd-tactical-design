package kitchenpos.menus.infra;


import kitchenpos.menus.domain.MenuGroupRepository;
import kitchenpos.menus.domain.MenuGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaMenuGroupRepository extends MenuGroupRepository, JpaRepository<MenuGroup, UUID> {
}
