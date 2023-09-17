package kitchenpos.menus.tobe.domain;

import kitchenpos.menus.domain.MenuGroup;
import kitchenpos.menus.domain.MenuGroupRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ToBeJpaMenuGroupRepository extends ToBeMenuGroupRepository, JpaRepository<ToBeMenuGroup, UUID> {
}
