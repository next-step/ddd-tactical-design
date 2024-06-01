package kitchenpos.menus.domain;

import kitchenpos.menus.tobe.domain.common.Price;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

@DisplayName("메뉴 도메인 가격 테스트")
public class PriceTest {

    @Test
    @DisplayName("동일한 가격인지 비교한다")
    void equals() {
        Price 메뉴_상품_가격 = new Price(BigDecimal.valueOf(20_000)).multiplyBy(5);
        Price 십만원 = new Price(BigDecimal.valueOf(100_000));

        Assertions.assertThat(메뉴_상품_가격.equals(십만원)).isTrue();
    }

    @Test
    @DisplayName("가격을 더한다.")
    void add() {
        Price 오천원 = new Price(BigDecimal.valueOf(5000));
        Price 만원 = new Price(BigDecimal.valueOf(10_000));
        Price 만_오천원 = 만원.add(오천원);
        Price real_만_오천원 = new Price(BigDecimal.valueOf(15_000));

        Assertions.assertThat(만_오천원.equals(real_만_오천원)).isTrue();
    }

    @Test
    @DisplayName("더 작은 가격인지 비교한다.")
    void isLessThen() {
        Price 오천원 = new Price(BigDecimal.valueOf(5000));
        Price 만원 = new Price(BigDecimal.valueOf(10_000));

        boolean result = 오천원.isLessThen(만원);
        Assertions.assertThat(result).isTrue();
    }
}
