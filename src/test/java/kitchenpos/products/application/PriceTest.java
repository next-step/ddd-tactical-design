package kitchenpos.products.application;

import kitchenpos.products.tobe.domain.Price;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductId;
import kitchenpos.products.tobe.domain.ProductName;
import kitchenpos.products.tobe.exception.NegativePriceException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

public class PriceTest {
    @DisplayName("상품의 가격이 올바르지 않으면 변경할 수 없다.")
    @ValueSource(strings = "-1000")
    @NullSource
    @ParameterizedTest
    void changePrice(final BigDecimal price) {
        assertThatThrownBy(() -> changePriceRequest(
                new ProductId(UUID.randomUUID()),
                new ProductName("치킨", new FakePurgomalumClient()),
                new Price(price)))
                .isInstanceOf(NegativePriceException.class);
    }

    private Product changePriceRequest(final ProductId productId, final ProductName productName, final Price price) {
        return new Product(
                productId,
                productName,
                price
        );
    }
}
