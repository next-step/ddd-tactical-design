package kitchenpos.menus.infra;

import kitchenpos.menus.tobe.domain.MenuGroup;
import kitchenpos.menus.tobe.domain.MenuGroupId;
import kitchenpos.menus.tobe.domain.MenuGroupRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaMenuGroupRepository extends MenuGroupRepository, JpaRepository<MenuGroup, MenuGroupId> {
}