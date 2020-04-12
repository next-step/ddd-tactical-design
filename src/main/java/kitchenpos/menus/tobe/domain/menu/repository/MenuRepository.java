package kitchenpos.menus.tobe.domain.menu.repository;

import kitchenpos.menus.tobe.domain.menu.domain.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Long> {
    int countByIdIn(List<Long> menuIds);
}
