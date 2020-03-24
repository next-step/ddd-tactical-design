package kitchenpos.menus.tobe.domain.menu.repository;

import kitchenpos.menus.tobe.domain.menu.domain.MenuGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuGroupRepository extends JpaRepository<MenuGroup, Long> {
}
