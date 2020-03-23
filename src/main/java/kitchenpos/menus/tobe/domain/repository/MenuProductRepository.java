package kitchenpos.menus.tobe.domain.repository;

import kitchenpos.menus.tobe.domain.MenuProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuProductRepository extends JpaRepository<MenuProduct, Long> {
}
