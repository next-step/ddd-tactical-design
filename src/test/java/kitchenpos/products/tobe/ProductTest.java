package kitchenpos.products.tobe;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;


@DisplayName("to-be 상품 도메인 테스트")
class ProductTest {


    @DisplayName("상품은 이름, 가격을 갖는다")
    @Test
    void createProductTest() {

        // given
        UUID uuid = UUID.randomUUID();

        // then
        assertDoesNotThrow(() -> {
            new Product(uuid, "상품 이름", BigDecimal.valueOf(1000));
        });
    }

    @DisplayName("이름은 공백을 허용한다")
    @EmptySource
    @ParameterizedTest
    void productNameWithBlankTest1(String emptyString) {

        // given
        UUID uuid = UUID.randomUUID();

        // when
        // then
        assertDoesNotThrow(() -> {
            var product = new Product(uuid, emptyString, BigDecimal.valueOf(1000));
        });

    }


    @Test
    @DisplayName("이름에는 비속어가 포함될 수 없다")
    void productNameWithBlankTest() {

        // given
        ProductValidator validator = (name, price) -> {
            throw new IllegalArgumentException("비속어가 포함되어 있습니다.");
        };

        // when
        // then
        assertThatThrownBy(() -> {
            new Product(UUID.randomUUID(), "비속어", BigDecimal.valueOf(1000), validator);
        }).isInstanceOf(IllegalArgumentException.class);
    }




    @DisplayName("가격이 0원 이상이어야 한다")
    @Test
    void productPriceIsZeroOrPositiveTest() {

        // given
        ProductValidator validator = (name, price) -> {
            throw new IllegalArgumentException("가격은 0보다 작을 수 없습니다.");
        };

        // when
        // then
        assertThatThrownBy(() -> {
            new Product(UUID.randomUUID(), "상품 이름", BigDecimal.ZERO, validator);
        }).isInstanceOf(IllegalArgumentException.class);
    }

}