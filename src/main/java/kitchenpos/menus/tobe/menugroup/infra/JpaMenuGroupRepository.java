package kitchenpos.menus.tobe.menugroup.infra;

import kitchenpos.menus.tobe.menugroup.domain.MenuGroup;
import kitchenpos.menus.tobe.menugroup.domain.MenuGroupRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaMenuGroupRepository extends MenuGroupRepository, JpaRepository<MenuGroup, UUID> {
}
