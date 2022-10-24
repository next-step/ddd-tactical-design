package kitchenpos.menu.tobe.infra.jpa;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaMenuDao extends JpaRepository<MenuEntity, UUID> {

    @Query("select m from MenuEntity as m where m.id in :ids")
    List<MenuEntity> findAllByIdIn(final List<UUID> ids);

    @Query("select m from MenuEntity as m join MenuProductEntity as mp on m.id = mp.menuId where mp.productId = :productId")
    List<MenuEntity> findAllByProductId(final UUID productId);
}
