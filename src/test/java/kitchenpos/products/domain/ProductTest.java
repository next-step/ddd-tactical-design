package kitchenpos.products.domain;

import kitchenpos.products.domain.tobe.Product;
import kitchenpos.products.domain.tobe.ProductPrice;
import kitchenpos.support.StubBanWordFilter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class ProductTest {

    @DisplayName("Product는 식별자와 ProductName, Price을 가진다.")
    @Test
    void create() {
        final String name = "순살치킨";
        final BigDecimal price = BigDecimal.valueOf(16000);
        final Product product = createProductWithValidName(name, price);

        assertAll(
                () -> assertThat(product).extracting("id").isNotNull(),
                () -> assertThat(product).extracting("name").isNotNull(),
                () -> assertThat(product).extracting("price").isNotNull()
        );
    }

    @DisplayName("상품의 가격을 변경하면 변경된 가격을 리턴한다.")
    @Test
    void changePrice() {
        final String name = "순살치킨";
        final BigDecimal price = BigDecimal.valueOf(16000);
        final BigDecimal changePrice = BigDecimal.valueOf(20000);
        final Product product = createProductWithValidName(name, price);

        product.changePrice(changePrice);

        assertThat(product.getPrice()).isEqualTo(new ProductPrice(changePrice));
    }

    public Product createProductWithValidName(String name, BigDecimal price) {
        final StubBanWordFilter banWordFilter = new StubBanWordFilter(false);
        return new Product(name, price, banWordFilter);
    }
}
