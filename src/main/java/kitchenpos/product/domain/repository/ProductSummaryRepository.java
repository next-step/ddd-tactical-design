package kitchenpos.product.domain.repository;

import kitchenpos.product.domain.model.ProductSummary;

public interface ProductSummaryRepository {
    ProductSummary save(ProductSummary productSummary);
}
