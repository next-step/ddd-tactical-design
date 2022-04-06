package kitchenpos.menus.infra;

import kitchenpos.menus.domain.MenuGroup;
import kitchenpos.menus.domain.tobe.MenuGroupRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaMenuGroupRepository extends MenuGroupRepository, JpaRepository<MenuGroup, UUID> {
}
