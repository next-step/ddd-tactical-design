package kitchenpos.menus.tobe.infra;

import java.util.List;
import java.util.UUID;
import kitchenpos.menus.tobe.domain.menu.Menu;
import kitchenpos.menus.tobe.domain.menu.MenuRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface JpaMenuRepository extends MenuRepository, JpaRepository<Menu, UUID> {
  @Query(
      "select m from Menu m join m.menuProducts.menuProducts mp where mp.product.id = :productId")
  @Override
  List<Menu> findAllByProductId(@Param("productId") UUID productId);
}
