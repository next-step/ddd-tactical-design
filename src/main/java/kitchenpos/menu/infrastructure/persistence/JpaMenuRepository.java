package kitchenpos.menu.infrastructure.persistence;

import java.util.List;
import kitchenpos.menu.domain.entity.Menu;
import kitchenpos.menu.domain.model.MenuId;
import kitchenpos.menu.domain.repository.MenuRepository;
import kitchenpos.product.domain.model.ProductId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

public interface JpaMenuRepository extends MenuRepository, JpaRepository<Menu, MenuId> {

    @Query("select m from Menu m " +
           "join m.menuProducts.menuProducts mp " +
           "where mp.productId = :productId")
    @Override
    List<Menu> findAllByProductId(@Param("productId") ProductId productId);
}
