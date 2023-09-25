package kitchenpos.menu.infra;

import java.math.BigDecimal;
import java.util.UUID;
import kitchenpos.menu.tobe.domain.MenuProduct;
import kitchenpos.menu.tobe.domain.MenuProductRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface JpaMenuProductRepository extends JpaRepository<MenuProduct, Long>, MenuProductRepository {

    @Modifying
    @Query("update MenuProduct m set m.price = :price where m.productId = :productId")
    @Override
    void bulkUpdateMenuProductPrice(@Param("productId") UUID productId, @Param("price")BigDecimal bigDecimal);
}
