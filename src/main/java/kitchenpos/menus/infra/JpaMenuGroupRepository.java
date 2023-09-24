package kitchenpos.menus.infra;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

import kitchenpos.menus.domain.MenuGroup;
import kitchenpos.menus.domain.MenuGroupRepository;

public interface JpaMenuGroupRepository extends MenuGroupRepository, JpaRepository<MenuGroup, UUID> {
}
