package kitchenpos.common;

import kitchenpos.common.WrongPriceException;
import kitchenpos.common.Price;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class PriceTest {

    @DisplayName("상품가격이 올바르지 않으면 ProductPriceException이 발생한다.")
    @ValueSource(ints = {-10000, -10})
    @ParameterizedTest
    void wrongProductPrice (final int price){
        assertThatExceptionOfType(WrongPriceException.class)
            .isThrownBy(() -> new Price(new BigDecimal(price)));
    }

    @DisplayName("상품가격은 0원 이상이다.")
    @ValueSource(ints = {0, 100, 1000})
    @ParameterizedTest
    void rightProductPrice (final int price){
        Price productPrice = new Price(new BigDecimal(price));
        assertThat(productPrice.valueOf()).isEqualTo(new BigDecimal(price));
    }

}
