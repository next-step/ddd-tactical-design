package kitchenpos.menus.tobe.domain;

import kitchenpos.NewFixtures;
import kitchenpos.common.domain.Price;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.util.UUID;

import static kitchenpos.menus.exception.MenuProductExceptionMessage.ILLEGAL_QUANTITY;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@DisplayName("메뉴상품 테스트")
class NewMenuProductTest {

    private final NewProduct newProduct = NewFixtures.newProduct(1_000L);

    @DisplayName("메뉴상품 생성 성공")
    @Test
    void create() {
        NewMenuProduct newMenuProduct = NewMenuProduct.create(newProduct, 3L);
        assertThat(newMenuProduct).isEqualTo(NewMenuProduct.create(newProduct, 3L));
    }

    @DisplayName("수량이 0보다 작으면 예외를 반환한다.")
    @ValueSource(longs = {-2L,-1L})
    @ParameterizedTest
    void quantity(Long input) {
        assertThatThrownBy(() -> NewMenuProduct.create(newProduct, input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ILLEGAL_QUANTITY);
    }

    @DisplayName("가격과 수량의 곱을 계산한다.")
    @Test
    void calculateTotalPrice() {
        NewMenuProduct menuProduct = NewMenuProduct.create(newProduct, 10L);

        Price calculatedPrice = menuProduct.calculateTotalPrice();

        assertThat(calculatedPrice).isEqualTo(Price.of(BigDecimal.valueOf(10_000L)));
    }


}
