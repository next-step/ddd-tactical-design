package kitchenpos.menus.domain;

import kitchenpos.menus.tobe.domain.menugroup.MenuGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaMenuGroupRepository extends MenuGroupRepository, JpaRepository<MenuGroup, UUID> {
}
