package kitchenpos.products.application;

import kitchenpos.products.tobe.domain.Price;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductId;
import kitchenpos.products.tobe.domain.ProductName;
import kitchenpos.products.tobe.exception.InvalidProductNameException;
import kitchenpos.tobe.Fixtures;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

public class ProductAsisNameTest {
    @DisplayName("상품의 이름이 올바르지 않으면 등록할 수 없다.")
    @ValueSource(strings = {"비속어", "욕설이 포함된 이름"})
    @NullSource
    @ParameterizedTest
    void create(final String name) { // Product 객체 생성 테스트로 빠져야 할까?
        assertThatThrownBy(() -> createProductRequest(name, BigDecimal.valueOf(16_000L)))
                .isInstanceOf(InvalidProductNameException.class);
    }
    private Product createProductRequest(final String name, final BigDecimal price) {
        final Product product = new Product(
                new ProductId(UUID.randomUUID()),
                new ProductName(name, Fixtures.purgomalumClient),
                new Price(price)
        );
        return product;
    }
}
