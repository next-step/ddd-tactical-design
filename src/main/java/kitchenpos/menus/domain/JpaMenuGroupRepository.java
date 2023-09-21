package kitchenpos.menus.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;
import kitchenpos.menus.tobe.domain.MenuGroup;

public interface JpaMenuGroupRepository extends MenuGroupRepository, JpaRepository<MenuGroup, UUID> {
}
