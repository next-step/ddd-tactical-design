package kitchenpos.menus.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface JpaMenuRepository extends MenuRepository, JpaRepository<Menu, UUID> {
    @Query("select m from Menu m join m.menuProducts.menuProducts mp where mp.productId = :productId")
    @Override
    List<Menu> findAllByProductId(@Param("productId") UUID productId);
}
