package kitchenpos.menus.infra;

import kitchenpos.menus.tobe.domain.menu.Menu;
import kitchenpos.menus.tobe.domain.menu.MenuId;
import kitchenpos.menus.tobe.domain.menu.MenuRepository;
import kitchenpos.menus.tobe.domain.menu.ProductId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JpaMenuRepository extends MenuRepository, JpaRepository<Menu, MenuId> {
    @Query("select m from Menu m join MenuProduct mp where mp.productId = :productId")
    @Override
    List<Menu> findAllByProductId(@Param("productId") ProductId productId);
}
