package kitchenpos.products.tobe.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ProductTest {
    private Product product;
    private DisplayedName displayedName;
    private Price price;

    @BeforeEach
    void setUp() {
        displayedName = DisplayedName.of("상품", (name -> false));
        price = new Price(BigDecimal.valueOf(1000));
        product = new Product(displayedName, price);
    }

    @Test
    @DisplayName("상품을 생성한다.")
    void create() {
        Product product = new Product(displayedName, price);
        assertThat(product).isNotNull();
    }

    @Test
    @DisplayName("상품 가격을 변경한다.")
    void changePrice() {
        long price = 2000;
        product.changePrice(BigDecimal.valueOf(price));
        assertThat(product.getPrice().toString()).isEqualTo(String.valueOf(price));
    }

    @Test
    @DisplayName("상품 가격변경시 가격은 0 이상이어야 한다.")
    void changePrice_validated_more_zero() {
        long price = -1;
        assertThatThrownBy(() -> product.changePrice(BigDecimal.valueOf(price)))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
