package kitchenpos.products.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static kitchenpos.ToBeFixtures.product;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@DisplayName("상품")
class ProductTest {

    private String displayedName = "강정 치킨";


    @DisplayName("상품을 생성한다.")
    @Test
    void createProduct() {
        assertDoesNotThrow(() -> product(displayedName, 20000));
    }

    @DisplayName("이름이 null이거나 빈칸인 상품을 생성할 수 없다.")
    @NullAndEmptySource
    @ParameterizedTest
    void createEmptyNameProduct(String name) {
        assertThatThrownBy(() -> product(name, 20000))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("상품명은 null 이나 공백일 수 없습니다.");
    }

    @DisplayName("0원보다 적은 가격으로 상품을 생성할 수 없다.")
    @ParameterizedTest
    @ValueSource(strings = "-1")
    void createNegativePriceProduct(long negativePrice) {
        assertThatThrownBy(() -> product(displayedName, negativePrice))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("0원 이하일 수 없습니다.");
    }

    @DisplayName("상품의 가격을 변경한다.")
    @ParameterizedTest
    @ValueSource(strings = "10000")
    void changeProductPrice(BigDecimal changePrice) {
        Product product = product(displayedName, 20000);
        product.changePrice(changePrice);
        assertThat(product.getPrice()).isEqualTo(new Price(changePrice));
    }

    @DisplayName("상품의 가격을 0원 이하로 변경할 수 없다.")
    @ParameterizedTest
    @ValueSource(strings = "-1")
    void changeNegativeProductPrice(BigDecimal negativePrice) {
        assertThatThrownBy(() -> product(displayedName, 20000).changePrice(negativePrice))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("0원 이하일 수 없습니다.");
    }

}
