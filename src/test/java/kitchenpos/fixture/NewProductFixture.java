package kitchenpos.fixture;

import kitchenpos.product.tobe.domain.Name;
import kitchenpos.product.tobe.domain.NewProduct;
import kitchenpos.product.tobe.domain.Price;

import java.math.BigDecimal;
import java.util.UUID;

public class NewProductFixture {
    public static NewProduct createNewProduct() {
        return createNewProduct("치킨", new BigDecimal(100));
    }

    public static NewProduct createNewProduct(final UUID id) {
        return createNewProduct(id, "치킨", new BigDecimal(100));
    }

    public static NewProduct createNewProduct(final String name, final BigDecimal price) {
        return createNewProduct(null, name, price);
    }

    public static NewProduct createNewProduct(final UUID id, final String name, final BigDecimal price) {
        return createNewProduct(id, new Name(name), new Price(price));
    }

    public static NewProduct createNewProduct(final UUID id, final Name name, final Price price) {
        return new NewProduct(id, name, price);
    }
}
