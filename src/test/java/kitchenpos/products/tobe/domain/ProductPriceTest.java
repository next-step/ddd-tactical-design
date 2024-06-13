package kitchenpos.products.tobe.domain;

import kitchenpos.products.tobe.exception.NegativeProductPriceException;
import kitchenpos.products.tobe.exception.ProductPriceNullPointException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ProductPriceTest {

    @DisplayName("상품의 가격이 마이너스이면 생성할 수 없다.")
    @ValueSource(strings = "-1000")
    @ParameterizedTest
    void invalidPrice(final BigDecimal price) {
        assertThatThrownBy(() -> ProductPrice.of(price))
                .isInstanceOf(NegativeProductPriceException.class);
    }

    @DisplayName("상품의 가격이 비어있으면 생성할 수 없다.")
    @NullSource
    @ParameterizedTest
    void emptyPrice(final BigDecimal price) {
        assertThatThrownBy(() -> ProductPrice.of(price))
                .isInstanceOf(ProductPriceNullPointException.class);
    }



}
