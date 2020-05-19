package kitchenpos.products.bo;

import kitchenpos.AbstractInMemoryJpaRepository;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductRepository;

public class InMemoryProductRepository extends AbstractInMemoryJpaRepository<Product, Long> implements ProductRepository {
}
