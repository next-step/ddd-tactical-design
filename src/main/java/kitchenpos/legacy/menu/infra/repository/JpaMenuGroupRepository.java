package kitchenpos.legacy.menu.infra.repository;

import kitchenpos.legacy.menu.domain.model.MenuGroup;
import kitchenpos.legacy.menu.domain.repository.MenuGroupRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaMenuGroupRepository extends MenuGroupRepository, JpaRepository<MenuGroup, UUID> {
}
