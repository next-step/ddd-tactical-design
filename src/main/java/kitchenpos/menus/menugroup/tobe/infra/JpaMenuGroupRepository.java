package kitchenpos.menus.menugroup.tobe.infra;

import kitchenpos.menus.menugroup.tobe.domain.MenuGroup;
import kitchenpos.menus.menugroup.tobe.domain.MenuGroupRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaMenuGroupRepository extends MenuGroupRepository, JpaRepository<MenuGroup, UUID> {
}
