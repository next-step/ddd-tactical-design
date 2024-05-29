package kitchenpos.menus.domain;

import kitchenpos.menus.tobe.domain.Price;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

@DisplayName("메뉴 도메인 가격 테스트")
public class PriceTest {

    @Test
    @DisplayName("가격을 비교한다")
    void equals() {
        Price 메뉴_상품_가격 = new Price(BigDecimal.valueOf(20_000), 5);
        Price 십만원 = new Price(BigDecimal.valueOf(100_000));

        Assertions.assertThat(메뉴_상품_가격.equals(십만원)).isTrue();
    }
}
