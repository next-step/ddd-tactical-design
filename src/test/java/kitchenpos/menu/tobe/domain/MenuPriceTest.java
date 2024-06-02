package kitchenpos.menu.tobe.domain;

import kitchenpos.exception.IllegalPriceException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertThrows;

class MenuPriceTest {

    @ParameterizedTest
    @NullSource
    @DisplayName("금액 정보는 필수로 입력해야한다.")
    void priceFail1(final Long input) {

        Assertions.assertThatExceptionOfType(IllegalPriceException.class)
                .isThrownBy(() -> new MenuPrice(input));
    }

    @ParameterizedTest
    @ValueSource(longs = {-1L, -10_000L})
    @DisplayName("0원보다 적은 금액을 입력하는 경우 메뉴가격을 등록할 수 없다.")
    void priceFail1(final long input) {

        assertThrows(IllegalPriceException.class, () -> new MenuPrice(input));
    }
}
