package kitchenpos.products.tobe.domain;

public class MockProductRepository implements ProductRepository {

    @Override
    public Product save(Product product) {
        return product;
    }
}
