package kitchenpos.menus.tobe.infra;

import kitchenpos.menus.tobe.domain.model.Menu;
import kitchenpos.menus.tobe.domain.repository.MenuRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository("Tobe_MenuRepository")
public interface JpaMenuRepository extends JpaRepository<Menu, UUID>, MenuRepository {

    @Override
    @Query(value = "" +
            "select * " +
            "from menu m " +
            "join menu_product mp on mp.menu_id = m.id and mp.product_id = :productId",
            nativeQuery = true)
    List<Menu> findAllByProductId(@Param("productId") UUID productId);
}
