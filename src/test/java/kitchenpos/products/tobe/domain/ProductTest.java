package kitchenpos.products.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

@SpringBootTest
class ProductTest {

    @Test
    @DisplayName("같은 값의 객체 비교")
    void compareProductByValueEquals() {
        // give
        Product friedChicken = new Product(1L, "후라이드 치킨", BigDecimal.valueOf(18_000));
        Product friedChickenAgain = new Product(1L, "후라이드 치킨", BigDecimal.valueOf(18_000));

        // when then
        assertThat(friedChicken.equals(friedChickenAgain)).isTrue();
    }

    @ParameterizedTest(name = "상품 번호 예외 처리")
    @ValueSource(longs = {-1L})
    void createExceptionById(Long id) {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> new Product(id, "후라이드 치킨", BigDecimal.valueOf(18_000)));
    }

    @ParameterizedTest(name = "상품 이름 예외 처리")
    @EmptySource
    @NullSource
    void createExceptionByName(String name) {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> new Product(1L, name, BigDecimal.valueOf(18_000)));
    }

    @ParameterizedTest(name = "상품 가격 예외처리")
    @ValueSource(longs = {-1_000L})
    void createExceptionByPrice(Long price) {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> new Product(1L, "후라이드 치킨", BigDecimal.valueOf(price)));
    }
}
