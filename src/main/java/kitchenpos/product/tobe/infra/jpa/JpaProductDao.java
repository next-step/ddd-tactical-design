package kitchenpos.product.tobe.infra.jpa;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
interface JpaProductDao extends JpaRepository<ProductEntity, UUID> {

    @Query("select p from ProductEntity as p where p.id in :ids")
    List<ProductEntity> findAllByIdIn(List<UUID> ids);
}
