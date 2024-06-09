package kitchenpos.menus.tobe.infra.repository;

import java.util.List;
import java.util.UUID;
import kitchenpos.menus.tobe.domain.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

interface JpaMenuRepository extends JpaRepository<Menu, UUID> {
    @Query("select m from Menu m join m.menuProducts mp where mp.productId = :productId")
    List<Menu> findMenusByProductId(@Param("productId") UUID productId);
}
