package kitchenpos.menus.infra;

import kitchenpos.menus.domain.tobe.menugroup.MenuGroup;
import kitchenpos.menus.domain.tobe.menugroup.TobeMenuGroupRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaMenuGroupRepository extends TobeMenuGroupRepository, JpaRepository<MenuGroup, UUID> {
}
