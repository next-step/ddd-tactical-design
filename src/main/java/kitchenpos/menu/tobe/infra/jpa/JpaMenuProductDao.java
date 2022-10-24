package kitchenpos.menu.tobe.infra.jpa;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaMenuProductDao extends JpaRepository<MenuProductEntity, Long> {

    @Query("select mp from MenuProductEntity as mp where mp.menuId = :menuId")
    List<MenuProductEntity> findAllByMenuId(final UUID menuId);

    @Query("delete from MenuProductEntity as mp where mp.menuId = :menuId")
    void deleteByMenuId(final UUID menuId);
}
