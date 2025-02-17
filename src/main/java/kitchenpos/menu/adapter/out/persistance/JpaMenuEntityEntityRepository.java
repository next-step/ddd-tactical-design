package kitchenpos.menu.adapter.out.persistance;

import kitchenpos.menu.adapter.out.persistance.entity.MenuEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface JpaMenuEntityEntityRepository extends MenuEntityRepository, JpaRepository<MenuEntity, UUID> {
    @Query("select m from MenuEntity m join m.menuProducts mp where mp.product.id = :productId")
    @Override
    List<MenuEntity> findAllByProductId(@Param("productId") UUID productId);
}
