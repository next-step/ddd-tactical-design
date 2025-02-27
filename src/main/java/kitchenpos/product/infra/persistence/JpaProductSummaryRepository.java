package kitchenpos.product.infra.persistence;

import java.util.UUID;
import kitchenpos.product.domain.model.ProductSummary;
import kitchenpos.product.domain.repository.ProductSummaryRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaProductSummaryRepository extends JpaRepository<ProductSummary, UUID>, ProductSummaryRepository {

}
