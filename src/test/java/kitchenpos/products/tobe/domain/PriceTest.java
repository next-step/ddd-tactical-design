package kitchenpos.products.tobe.domain;

import kitchenpos.common.domain.Price;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class PriceTest {

    @ParameterizedTest
    @ValueSource(longs = {0L, -1L, -1000L, -10000000L})
    @DisplayName("0원 이하 입력 시 가격 생성 실패")
    void createPriceTest(long price) {
        assertThatIllegalArgumentException().isThrownBy(() -> new Price(price));
    }
}
