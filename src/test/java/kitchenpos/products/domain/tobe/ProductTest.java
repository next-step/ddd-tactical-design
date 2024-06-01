package kitchenpos.products.domain.tobe;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    @DisplayName("상품을 등록할 수 있다.")
    @Test
    public void createProductTest() {
        final Product expected = createProductRequest("후라이드", new BigDecimal(16000));

        Product actual = Product.createProduct("후라이드", new BigDecimal(16000), new FakeProfanities());

        assertThat(actual).isNotNull();
        assertAll(
                () -> assertThat(actual.getDispayedName().getName()).isEqualTo(expected.getDispayedName().getName()),
                () -> assertThat(actual.getPrice().getPriceValue()).isEqualTo(expected.getPrice().getPriceValue())
        );
    }

    @DisplayName("상품의 가격이 올바르지 않으면 등록할 수 없다.")
    @ValueSource(strings = "-1000")
    @NullSource
    @ParameterizedTest
    void create(final BigDecimal price) {
        assertThatThrownBy(() -> Product.createProduct("후라이드", price, new FakeProfanities()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("상품의 이름이 올바르지 않으면 등록할 수 없다.")
    @ValueSource(strings = {"비속어", "욕설이 포함된 이름"})
    @NullSource
    @ParameterizedTest
    void create(final String name) {
        assertThatThrownBy(() -> Product.createProduct(name, new BigDecimal(16000), new FakeProfanities()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("상품의 가격을 변경할 수 있다.")
    @Test
    void changePrice() {
        final Product product = createProductRequest("후라이드", new BigDecimal(16000));

        final BigDecimal changePrice = BigDecimal.valueOf(15000);
        final Product actual = product.changePrice(changePrice);

        assertThat(actual.getPrice().getPriceValue()).isEqualTo(changePrice);
    }

    @DisplayName("상품의 가격이 올바르지 않으면 변경할 수 없다.")
    @ValueSource(strings = "-1000")
    @NullSource
    @ParameterizedTest
    void changePrice(final BigDecimal price) {
        final Product product = createProductRequest("후라이드", new BigDecimal(16000));

        assertThatThrownBy(() -> product.changePrice(price))
                .isInstanceOf(IllegalArgumentException.class);
    }

    private Product createProductRequest(String name, BigDecimal price) {
        return Product.createProduct(name, price, new FakeProfanities());
    }

}
