package kitchenpos.products.tobe.domain.fixture;

import kitchenpos.common.FakeProfanities;
import kitchenpos.common.domain.Name;
import kitchenpos.common.domain.Price;
import kitchenpos.products.tobe.domain.model.Product;

import java.math.BigDecimal;

public class ProductFixture {

    public static Product PRODUCT_FIXTURE(String nameValue, Long priceValue) {
        return new Product(new Name(nameValue, new FakeProfanities()), new Price(BigDecimal.valueOf(priceValue)));
    }

}
