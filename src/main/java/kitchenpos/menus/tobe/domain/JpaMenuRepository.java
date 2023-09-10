package kitchenpos.menus.tobe.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface JpaMenuRepository extends MenuRepository, JpaRepository<kitchenpos.menus.tobe.domain.Menu, UUID> {
    @Query("select m from Menu m join m.menuProducts mp where mp.product.id = :productId")
    @Override
    List<kitchenpos.menus.tobe.domain.Menu> findAllByProductId(@Param("productId") UUID productId);
}
