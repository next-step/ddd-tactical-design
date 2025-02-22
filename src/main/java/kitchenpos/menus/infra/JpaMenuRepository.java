package kitchenpos.menus.infra;

import kitchenpos.menus.tobe.domain.Menu;
import kitchenpos.menus.tobe.domain.MenuId;
import kitchenpos.menus.tobe.domain.MenuRepository;
import kitchenpos.products.tobe.domain.ProductId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JpaMenuRepository extends MenuRepository, JpaRepository<Menu, MenuId> {

    @Query("select m from Menu m join m.menuProducts mp where mp.product.id = :productId")
    @Override
    List<Menu> findAllByProductId(@Param("productId") ProductId productId);
}
