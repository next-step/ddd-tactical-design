package kitchenpos.menus.tobe.infra;

import java.util.List;
import java.util.UUID;
import kitchenpos.menus.tobe.domain.model.Menu;
import kitchenpos.menus.tobe.domain.repository.MenuRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository("tobeJpaMenuRepository")
public interface JpaMenuRepository extends MenuRepository, JpaRepository<Menu, UUID> {
    @Query("select m from Menu m, MenuProduct mp where mp.product.id = :productId")
    @Override
    List<Menu> findAllByProductId(@Param("productId") UUID productId);
}
