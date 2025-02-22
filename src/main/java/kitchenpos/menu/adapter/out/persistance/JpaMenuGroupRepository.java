package kitchenpos.menu.adapter.out.persistance;

import kitchenpos.menu.application.port.out.MenuGroupRepository;
import kitchenpos.menu.domain.MenuGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaMenuGroupRepository extends MenuGroupRepository, JpaRepository<MenuGroup, UUID> {
}
