package kitchenpos.menus.tobe.repository;

import java.util.UUID;

import kitchenpos.menus.tobe.domain.MenuGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaMenuGroupRepository extends MenuGroupRepository, JpaRepository<MenuGroup, UUID> {
}
