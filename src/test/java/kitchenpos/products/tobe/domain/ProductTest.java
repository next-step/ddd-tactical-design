package kitchenpos.products.tobe.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.math.BigDecimal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class ProductTest {

    @DisplayName("상품을 등록할수 있다")
    @ParameterizedTest
    @ValueSource(longs = {1, 100, 1_000, 10_000, 100_000, 1_000_000})
    void test1(long price) {
        //given
        String name = "name";

        //when
        Product product = new Product(name, BigDecimal.valueOf(price));

        //then
        assertAll(
            () -> assertThat(product.getId()).isNotNull(),
            () -> assertThat(product.getName()).isEqualTo(name),
            () -> assertThat(product.getPrice()).isEqualTo(BigDecimal.valueOf(price))
        );

    }

    @DisplayName("가격이 음수인 상품은 등록할수 없다")
    @ParameterizedTest
    @ValueSource(longs = {-1, -100, -1_000, -10_000, -100_000, -1_000_000})
    void test2(long price) {
        assertThatThrownBy(
            () -> new Product("name", BigDecimal.valueOf(price))
        ).isInstanceOf(IllegalArgumentException.class)
            .hasMessage("가격은 음수가 될수 없습니다");
    }


}
