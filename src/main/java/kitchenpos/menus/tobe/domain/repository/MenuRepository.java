package kitchenpos.menus.tobe.domain.repository;

import kitchenpos.menus.tobe.domain.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu, Long> {
}
