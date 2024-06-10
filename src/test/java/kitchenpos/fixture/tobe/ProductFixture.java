package kitchenpos.fixture.tobe;

import jakarta.validation.constraints.NotNull;
import kitchenpos.infra.FakePurgomalumClient;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductName;
import kitchenpos.products.tobe.domain.ProductPrice;

import java.math.BigDecimal;

import static kitchenpos.MoneyConstants.만원;

public class ProductFixture {

    public static final String 상품명 = "상품명";
    public static FakePurgomalumClient fakePurgomalumClient = new FakePurgomalumClient();

    public static Product createProductWithoutName() {
        return createProduct(null, 만원);
    }

    public static Product createProductWithoutPrice() {
        return createProduct(상품명, null);
    }

    public static Product createProduct() {
        return createProduct(상품명, 만원);
    }

    public static Product createProduct(long price) {
        return createProduct(상품명, price);
    }

    public static Product createProduct(String name) {
        return createProduct(name, 만원);
    }

    public static @NotNull Product createProduct(String name, Long price) {
        return new Product(new ProductName(name, fakePurgomalumClient),
                new ProductPrice(BigDecimal.valueOf(price)));
    }
}
