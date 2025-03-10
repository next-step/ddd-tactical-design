package kitchenpos.menus.tobe.infra.persistence.product;

import java.math.BigDecimal;
import java.util.UUID;
import kitchenpos.menus.tobe.domain.ProductPriceService;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductId;
import kitchenpos.products.tobe.domain.TobeProductRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface JpaProductPriceRepository extends TobeProductRepository,
    JpaRepository<Product, ProductId>, ProductPriceService {

    @Query("SELECT p.price FROM Product p WHERE p.id = :id")
    BigDecimal findPriceByProductId(@Param("id") UUID productId);
}
