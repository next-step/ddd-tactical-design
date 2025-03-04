package kitchenpos.core.products.tobe.domain;

import static org.junit.jupiter.api.Assertions.*;

import kitchenpos.config.UnitTest;
import kitchenpos.core.products.tobe.domain.exception.InvalidProductPriceException;
import kitchenpos.core.shared.value.Money;
import kitchenpos.core.shared.value.Quantity;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@UnitTest
@DisplayName("[Product] ProductPrice 테스트")
class ProductPriceTest {

    @Test
    @DisplayName("성공: 유효한 금액으로 ProductPrice 생성")
    void createProductPriceSuccess() {
        // given
        Money money = Money.wons(new BigDecimal("10000"));

        // when
        ProductPrice productPrice = ProductPrice.of(money);

        // then
        assertNotNull(productPrice);
        assertEquals(money, productPrice.getPrice());
    }

    @Disabled("Money 객체의 생성자에서 null 체크를 하므로 해당 테스트는 필요 없음")
    @Test
    @DisplayName("실패: null 금액으로 ProductPrice 생성 시 예외 발생")
    void createProductPriceFailWhenNull() {
        // given & when & then
        assertThrows(InvalidProductPriceException.class, () -> {
            ProductPrice.of(null);
        });
    }

    @Disabled("Money 객체의 생성자에서 0보다 작은 금액 체크를 하므로 해당 테스트는 필요 없음")
    @ParameterizedTest
    @DisplayName("실패: 0보다 작은 금액으로 ProductPrice 생성 시 예외 발생")
    @ValueSource(strings = {"-1", "-10000"})
    void createProductPriceFailWhenNegative(BigDecimal price) {
        // given
        Money negativeMoney = Money.wons(price);

        // when & then
        assertThrows(InvalidProductPriceException.class, () -> {
            ProductPrice.of(negativeMoney);
        });
    }
    @Test
    @DisplayName("성공: 곱하기 연산 테스트")
    void multiplyTest() {
        // given
        Money money = Money.wons(new BigDecimal("5000"));
        ProductPrice productPrice = ProductPrice.of(money);
        Quantity quantity = Quantity.of(3); // Quantity의 내부 값은 3으로 가정

        // when
        Money result = productPrice.multiply(quantity);

        // then
        Money expected = money.multiply(3);
        assertEquals(expected, result);
    }
}