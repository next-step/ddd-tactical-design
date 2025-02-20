package kitchenpos.menu.infra.persistence;

import kitchenpos.menu.domain.model.MenuGroup;
import kitchenpos.menu.domain.repository.MenuGroupRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaMenuGroupRepository extends MenuGroupRepository, JpaRepository<MenuGroup, UUID> {
}
