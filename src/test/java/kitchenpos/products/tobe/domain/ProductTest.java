package kitchenpos.products.tobe.domain;

import kitchenpos.products.tobe.fakes.FakePurgomalums;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class ProductTest {

    private final ProfanitiesChecker profanitiesChecker = new FakePurgomalums("비속어", "욕설");

    @DisplayName("상품을 생성한다.")
    @Test
    void createProduct() {
        Product product = new Product("후라이드", profanitiesChecker, 10000L);

        assertThat(product.getId()).isNotNull();
        assertThat(product.getDisplayedName().getName()).isEqualTo("후라이드");
        assertThat(product.getPrice().getValue()).isEqualTo(10000L);
    }

    @DisplayName("상품의 가격을 변경할 수 있다.")
    @Test
    void changePrice() {
        Product product = new Product("후라이드", profanitiesChecker, 10000L);
        Price price = new Price(11000L);

        product.changePrice(price);

        assertThat(product.getPrice()).isEqualTo(price);
    }
}
