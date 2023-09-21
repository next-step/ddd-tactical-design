package kitchenpos.menus.tobe.infra;

import kitchenpos.menus.tobe.domain.menu.TobeMenu;
import kitchenpos.menus.tobe.domain.menu.TobeMenuRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface TobeJpaMenuRepository extends TobeMenuRepository, JpaRepository<TobeMenu, UUID> {
    @Query("select m from TobeMenu m join m.tobeMenuProducts.tobeMenuProducts mp where mp.productId = :productId")
    @Override
    List<TobeMenu> findAllByProductId(@Param("productId") UUID productId);
}
