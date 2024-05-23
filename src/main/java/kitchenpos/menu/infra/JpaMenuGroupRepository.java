package kitchenpos.menu.infra;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import kitchenpos.menu.domain.MenuGroup;
import kitchenpos.menu.domain.MenuGroupRepository;

public interface JpaMenuGroupRepository extends MenuGroupRepository, JpaRepository<MenuGroup, UUID> {
}
