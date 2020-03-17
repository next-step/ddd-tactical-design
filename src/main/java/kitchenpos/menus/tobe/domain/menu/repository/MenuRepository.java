package kitchenpos.menus.tobe.domain.menu.repository;

import kitchenpos.menus.tobe.domain.menu.domain.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu, Long> {
}
