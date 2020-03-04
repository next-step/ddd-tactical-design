package kitchenpos.products.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ProductDomainTests {
    @DisplayName("가격이 0원 이상인 상품 생성 시 성공")
    @ParameterizedTest
    @ValueSource(ints = 16000)
    public void createProductWithValidPrice(int price) {
        Product product = Product.registerProduct("후라이드치킨", price);

        assertThat(product.getName()).isEqualTo("후라이드치킨");
        assertThat(product.getPrice()).isEqualTo(BigDecimal.valueOf(price));
    }

    @DisplayName("가격이 0원 미만인 상품 생성 시 실패")
    @ParameterizedTest
    @ValueSource(ints = {-1 , -100})
    public void createProductWithInvalidPrice(int price) {
        assertThatThrownBy(() -> {
            Product.registerProduct("후라이드치킨", price);
        }).isInstanceOf(IllegalArgumentException.class);
    }
}
