package kitchenpos.products.tobe;

import org.junit.jupiter.api.BeforeEach;
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

    private ProductValidator validator;

    @BeforeEach
    void setUp() {
        validator = new ProductValidator() {
            @Override
            public void delegate(String name, BigDecimal price) {
                validateName(name);
            }

            @Override
            public void validateName(String name) {
                if (name == null) {
                    throw new IllegalArgumentException("상품명은 null일 수 없습니다");
                }
                if ("비속어".equals(name)) {
                    throw new IllegalArgumentException("비속어가 포함되어 있습니다.");
                }
            }
        };
    }

    @DisplayName("상품은 이름, 가격을 갖는다")
    @Test
    void createProductTest() {

        // given
        UUID uuid = UUID.randomUUID();

        // then
        assertDoesNotThrow(() -> {
            new Product(uuid, "상품 이름", new Money(BigDecimal.valueOf(1000)));
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
            var product = new Product(uuid, emptyString, new Money(BigDecimal.valueOf(1000)));
        });

    }


    @Test
    @DisplayName("이름에는 비속어가 포함될 수 없다")
    void productNameWithBlankTest() {

        // given
        // when
        // then
        assertThatThrownBy(() -> {
            new Product(UUID.randomUUID(), "비속어", new Money(BigDecimal.valueOf(1000)), validator);
        }).isInstanceOf(IllegalArgumentException.class);
    }




    @DisplayName("가격이 0원 이상이어야 한다")
    @Test
    void productPriceIsZeroOrPositiveTest() {

        // given
        // when
        // then
        assertThatThrownBy(() -> {
            new Product(UUID.randomUUID(), "상품 이름", new Money(BigDecimal.valueOf(-1)), validator);
        }).isInstanceOf(IllegalArgumentException.class);
    }

}