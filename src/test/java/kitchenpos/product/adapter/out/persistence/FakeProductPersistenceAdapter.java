package kitchenpos.product.adapter.out.persistence;

import org.assertj.core.util.VisibleForTesting;

@VisibleForTesting
public class FakeProductPersistenceAdapter extends ProductPersistenceAdapter {

    public FakeProductPersistenceAdapter(ProductRepository productRepository) {
        super(productRepository);
    }
}