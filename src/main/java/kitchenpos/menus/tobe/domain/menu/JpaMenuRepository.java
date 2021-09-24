package kitchenpos.menus.tobe.domain.menu;

import kitchenpos.common.domain.MenuId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaMenuRepository extends MenuRepository, JpaRepository<Menu, MenuId> {

    // @Query("select m from Menu m, MenuProduct mp where mp.productId = :productId")
    // @Override
    // List<Menu> findAllByProductId(@Param("productId") ProductId productId);
}
