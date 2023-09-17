package kitchenpos.menus.tobe.infra;

import kitchenpos.menus.domain.MenuGroup;
import kitchenpos.menus.domain.MenuGroupRepository;
import kitchenpos.menus.tobe.domain.ToBeMenuGroup;
import kitchenpos.menus.tobe.domain.ToBeMenuGroupRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ToBeJpaMenuGroupRepository extends ToBeMenuGroupRepository, JpaRepository<ToBeMenuGroup, UUID> {
}
