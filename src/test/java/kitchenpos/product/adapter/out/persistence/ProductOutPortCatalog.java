package kitchenpos.product.adapter.out.persistence;

import kitchenpos.product.application.port.out.LoadProductPort;
import kitchenpos.product.application.port.out.UpdateProductPort;

public class ProductOutPortCatalog {

    private FakeProductPersistenceAdapter productPersistenceAdapter;

    public ProductOutPortCatalog() {
        productPersistenceAdapter = new FakeProductPersistenceAdapter(new InMemoryProductRepository());
    }

    public LoadProductPort loadProductPort() {
        return productPersistenceAdapter;
    }

    public UpdateProductPort updateProductPort() {
        return productPersistenceAdapter;
    }
}
