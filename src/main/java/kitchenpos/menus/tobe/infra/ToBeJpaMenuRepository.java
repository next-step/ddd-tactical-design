package kitchenpos.menus.tobe.infra;

import kitchenpos.menus.domain.Menu;
import kitchenpos.menus.domain.MenuRepository;
import kitchenpos.menus.tobe.domain.ToBeMenu;
import kitchenpos.menus.tobe.domain.ToBeMenuRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface ToBeJpaMenuRepository extends ToBeMenuRepository, JpaRepository<ToBeMenu, UUID> {
    @Query("select m from Menu m join m.menuProducts mp where mp.product.id = :productId")
    @Override
    List<ToBeMenu> findAllByProductId(@Param("productId") UUID productId);
}
