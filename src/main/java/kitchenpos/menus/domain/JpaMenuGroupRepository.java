package kitchenpos.menus.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import kitchenpos.menus.tobe.domain.MenuGroup;

import java.util.UUID;

public interface JpaMenuGroupRepository extends MenuGroupRepository, JpaRepository<MenuGroup, UUID> {
}
