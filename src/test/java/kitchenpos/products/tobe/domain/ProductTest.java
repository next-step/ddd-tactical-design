package kitchenpos.products.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class ProductTest {

    @DisplayName("`Product`는 `id`를 `getId`한다.")
    @Test
    void getId() {
        final UUID expected = UUID.randomUUID();
        final Product product = product(expected);

        assertThat(product.getId()).isEqualTo(expected);
    }

    @DisplayName("`Product`는 `DisplayedName`의 `name`을 `getName`한다.")
    @ValueSource(strings = "후라이드")
    @ParameterizedTest
    void getName(final String name) {
        final Product product = product(name);

        assertThat(product.getName()).isEqualTo(name);
    }

    @DisplayName("`Product`는 `Price`의 `price`를 `getPrice`한다.")
    @ValueSource(strings = "16000")
    @ParameterizedTest
    void getPrice(final BigDecimal price) {
        final Product product = product(price);

        assertThat(product.getPrice()).isEqualTo(price);
    }

    @DisplayName("`Product`는 `Price`로 `changePrice`한다.")
    @ValueSource(strings = "20000")
    @ParameterizedTest
    void changePrice(final BigDecimal expected) {
        final Price price = new Price(expected);
        final Product product = product();

        product.changePrice(price);

        assertThat(product.getPrice()).isEqualTo(expected);
    }

    public Product product(final UUID id, final String name, final BigDecimal price) {
        return new Product(id, new DisplayedName(name), new Price(price));
    }

    public Product product() {
        return product(UUID.randomUUID(), "후라이드", BigDecimal.valueOf(16_000L));
    }

    public Product product(final UUID id) {
        return product(id, "후라이드", BigDecimal.valueOf(16_000L));
    }

    public Product product(final String name) {
        return product(UUID.randomUUID(), name, BigDecimal.valueOf(16_000L));
    }

    public Product product(final BigDecimal price) {
        return product(UUID.randomUUID(), "후라이드", price);
    }
}
