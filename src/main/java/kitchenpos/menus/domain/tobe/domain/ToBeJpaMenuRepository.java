package kitchenpos.menus.domain.tobe.domain;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ToBeJpaMenuRepository extends ToBeMenuRepository, JpaRepository<ToBeMenu, UUID> {
    @Query("select m from ToBeMenu m join m.menuProducts mp where mp.product.id = :productId")
    @Override
    List<ToBeMenu> findAllByProductId(@Param("productId") UUID productId);
}
