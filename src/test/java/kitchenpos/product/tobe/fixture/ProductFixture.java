package kitchenpos.product.tobe.fixture;


import kitchenpos.product.tobe.Profanities;
import kitchenpos.product.tobe.domain.Product;
public class ProductFixture {
    public static Product product(String name, final long price, Profanities profanities) {
        return new Product(name, price, profanities);
    }
}
