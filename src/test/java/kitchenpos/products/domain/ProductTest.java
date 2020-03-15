package kitchenpos.products.domain;

import kitchenpos.products.tobe.domain.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class ProductTest {

    @Test
    void createProduct() {
        String name = "갈릭치킨";
        BigDecimal price = BigDecimal.valueOf(10000L);
        Product product = new Product(name, price);

        assertThat(product.getName()).isEqualTo(name);
        assertThat(product.getPrice()).isEqualTo(price);
    }

    @DisplayName("상품의 가격이 올바르지 않으면 등록할 수 없다.")
    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"-1", "-10000"})
    void validPrice(BigDecimal price) {
        String name = "갈릭치킨";
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> new Product(name, price));
    }


}
