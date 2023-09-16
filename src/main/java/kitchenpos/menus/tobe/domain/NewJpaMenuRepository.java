package kitchenpos.menus.tobe.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface NewJpaMenuRepository extends MenuRepository, JpaRepository<NewMenu, UUID> {
    @Query("select m from NewMenu m join m.menuProducts.newMenuProducts mp where mp.productId = :productId")
    @Override
    List<NewMenu> findAllByProductId(@Param("productId") UUID productId);
}
