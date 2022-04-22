package kitchenpos.eatinorders.tobe.domain;

import kitchenpos.eatinorders.tobe.exception.InvalidStockQuantityException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class StockTest {

    @ParameterizedTest
    @ValueSource(longs = { 0, -1, -1000, -10000 })
    @DisplayName("수량을 1 이상 입력하지 않으면 재고 생성 실패")
    void createStockFail(long quantity) {
        assertThatExceptionOfType(
            InvalidStockQuantityException.class).isThrownBy(() -> new Stock(quantity));
    }

    @ParameterizedTest
    @ValueSource(longs = { 1, 2, 3, 100, 30000, 500000 })
    @DisplayName("수량을 1 이상 입력하면 재고 생성 성공")
    void createStockSuccess(long quantity) {
        assertThatCode(() -> new Stock(quantity)).doesNotThrowAnyException();
    }
}
