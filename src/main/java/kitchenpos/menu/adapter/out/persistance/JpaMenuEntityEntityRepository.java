package kitchenpos.menu.adapter.out.persistance;

import kitchenpos.menu.adapter.out.persistance.entity.MenuEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface JpaMenuEntityEntityRepository extends JpaRepository<MenuEntity, UUID> {
    @Query("""
        SELECT DISTINCT m
        FROM MenuEntity m
        JOIN FETCH m.menuProducts mp
        WHERE mp.productId = :productId
    """)
    List<MenuEntity> findAllByProductId(@Param("productId") UUID productId);
    List<MenuEntity> findAllByIdIn(List<UUID> ids);
}
