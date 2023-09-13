package kitchenpos.apply.menus.tobe.infra;

import kitchenpos.apply.menus.tobe.domain.Menu;
import kitchenpos.apply.menus.tobe.domain.MenuRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface JpaMenuRepository extends MenuRepository, JpaRepository<Menu, UUID> {
    @Query("select distinct m from Menu m join fetch m.menuProducts.menuProductList mp where mp.productId = :productId")
    @Override
    List<Menu> findAllByProductId(@Param("productId") UUID productId);
}
