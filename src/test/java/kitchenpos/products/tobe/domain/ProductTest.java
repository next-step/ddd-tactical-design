package kitchenpos.products.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ProductTest {

    @Test
    @DisplayName("이름과 가격을 입력하여 상품을 만들 수 있다.")
    void product() {
        Product product = new Product("상품이름", 1000L);

        Price price = new Price(1000L);
        DisplayedName displayedName = new DisplayedName("상품이름");

        assertThat(product.getId()).isNotNull();
        assertThat(product.getPrice()).isEqualTo(price);
        assertThat(product.getDisplayedName()).isEqualTo(displayedName);
    }

    @Test
    @DisplayName("이름이 비어있으면 상품을 만들 수 없다.")
    void product_empty_name() {
        assertThatThrownBy(() -> new Product("", 1000L))
                .isInstanceOf(IllegalArgumentException.class);
    }


    @Test
    @DisplayName("가격이 0보다 작으면 상품을 만들 수 없다.")
    void product_positive_price() {
        assertThatThrownBy(() -> new Product("상품이름", -1L))
                .isInstanceOf(IllegalArgumentException.class);
    }
}