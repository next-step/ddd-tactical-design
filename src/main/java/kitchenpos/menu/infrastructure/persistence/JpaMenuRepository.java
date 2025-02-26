package kitchenpos.menu.infrastructure.persistence;

import java.util.List;
import java.util.UUID;
import kitchenpos.menu.domain.entity.Menu;
import kitchenpos.menu.domain.repository.MenuRepository;
import kitchenpos.product.domain.model.ProductId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface JpaMenuRepository extends MenuRepository, JpaRepository<Menu, UUID> {

    @Query("select m from Menu m join MenuProduct mp where mp.productId = :productId")
    @Override
    List<Menu> findAllByProductId(@Param("productId") ProductId productId);
}
