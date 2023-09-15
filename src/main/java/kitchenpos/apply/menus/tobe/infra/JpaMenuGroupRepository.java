package kitchenpos.apply.menus.tobe.infra;

import kitchenpos.apply.menus.tobe.domain.MenuGroup;
import kitchenpos.apply.menus.tobe.domain.MenuGroupRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaMenuGroupRepository extends MenuGroupRepository, JpaRepository<MenuGroup, UUID> {
}
