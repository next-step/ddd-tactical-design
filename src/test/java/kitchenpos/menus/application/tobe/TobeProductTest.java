package kitchenpos.menus.application.tobe;

import kitchenpos.menus.tobe.domain.ProductName;
import kitchenpos.menus.tobe.domain.ProductPrice;
import kitchenpos.menus.tobe.domain.TobeProduct;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class TobeProductTest {

    @DisplayName("상품 생성")
    @Test
    void create() {
        TobeProduct product = createProduct("후라이드", 16_000L);

        assertThat(product).isNotNull();
    }

    @DisplayName("상품의 가격이 올바르지 않으면 등록할 수 없다.")
    @ValueSource(strings = "-1000")
    @NullSource
    @ParameterizedTest
    void create(final BigDecimal price) {
        assertThatThrownBy(() -> createProduct("후라이드", price))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("상품의 이름이 올바르지 않으면 등록할 수 없다.")
    @NullSource
    @ParameterizedTest
    void create(final String name) {
        assertThatThrownBy(() -> createProduct(name, 16_000L))
            .isInstanceOf(IllegalArgumentException.class);
    }

    private TobeProduct createProduct(final String name, final long price) {
        return createProduct(name, BigDecimal.valueOf(price));
    }

    private TobeProduct createProduct(final String name, final BigDecimal price) {
        final ProductName productName = new ProductName(name);
        final ProductPrice productPrice = new ProductPrice(price);
        return new TobeProduct(UUID.randomUUID(), productName, productPrice);
    }
}
