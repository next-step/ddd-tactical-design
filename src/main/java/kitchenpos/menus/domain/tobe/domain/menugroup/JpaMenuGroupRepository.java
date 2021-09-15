package kitchenpos.menus.domain.tobe.domain.menugroup;

import kitchenpos.common.domain.MenuGroupId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaMenuGroupRepository extends MenuGroupRepository, JpaRepository<MenuGroup, MenuGroupId> {
}
