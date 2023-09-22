package kitchenpos.menus.infra.jpa;

import kitchenpos.menus.domain.Menu;
import kitchenpos.menus.domain.MenuRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface JpaMenuRepository extends MenuRepository, JpaRepository<Menu, UUID> {
    @Query(value = "select m from Menu m join m.menuProducts mp where mp.menuProducts.productId = :productId")
    @Override
    List<Menu> findAllByProductId(@Param("productId") UUID productId);
}
