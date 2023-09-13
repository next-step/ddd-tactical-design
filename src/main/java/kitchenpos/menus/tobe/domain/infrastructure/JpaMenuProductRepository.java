package kitchenpos.menus.tobe.domain.infrastructure;

import java.util.List;
import java.util.UUID;
import kitchenpos.menus.tobe.domain.MenuProduct;
import kitchenpos.menus.tobe.domain.MenuProductRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaMenuProductRepository extends JpaRepository<MenuProduct, Long>, MenuProductRepository {

    List<MenuProduct> findAllByProductId(UUID productId);
}
