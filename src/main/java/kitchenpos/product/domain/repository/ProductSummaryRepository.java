package kitchenpos.product.domain.repository;

import kitchenpos.product.domain.model.ProductSummary;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductSummaryRepository {
    ProductSummary save(ProductSummary productSummary);
}
