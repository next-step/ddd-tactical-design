package kitchenpos.menus.domain.tobe.domain;

import kitchenpos.menus.domain.Menu;
import kitchenpos.menus.domain.MenuRepository;
import kitchenpos.menus.domain.tobe.domain.vo.MenuId;
import kitchenpos.products.domain.tobe.domain.vo.ProductId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface TobeJpaMenuRepository extends TobeMenuRepository, JpaRepository<TobeMenu, MenuId> {
    @Query("select m from Menu m, MenuProduct mp where mp.product.id = :productId")
    @Override
    List<TobeMenu> findAllByProductId(@Param("productId") ProductId productId);
}
