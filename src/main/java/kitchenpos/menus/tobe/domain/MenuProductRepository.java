package kitchenpos.menus.tobe.domain;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuProductRepository extends JpaRepository<MenuProduct, Long> {

    List<MenuProduct> findByMenuId(Long menuId);
}
