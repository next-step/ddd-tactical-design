package kitchenpos.menus.domain;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface JpaMenuProductRepository extends MenuProductRepository, JpaRepository<MenuProduct, Long> {

    @Query("select mp from MenuProduct mp where mp.productId = :productId")
    @Override
    List<MenuProduct> findAllByProductId(UUID productId);
}
