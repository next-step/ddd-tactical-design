package kitchenpos.domain.product.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class ProductTest {

    @DisplayName("상품 이름이 Null이 아니고 상품 가격이 0원 이상이면 상품 생성을 성공한다")
    @ValueSource(ints = {0, 1, 1_000})
    @ParameterizedTest
    void constructor(int price) {
        String productName = "상품A";
        BigDecimal productPrice = BigDecimal.valueOf(price);
        Product product = new Product(UUID.randomUUID(), productName, productPrice);
        assertThat(product.name()).isEqualTo(productName);
        assertThat(product.price()).isEqualTo(productPrice);
    }

    @DisplayName("상품 이름이 Null이면 상품 생성을 실패한다")
    @Test
    void constructor_name_fail() {
        assertThatIllegalArgumentException().isThrownBy(() -> new Product(null, BigDecimal.ZERO));
    }

    @DisplayName("상품 가격이 음수이면 상품 생성을 실패한다")
    @Test
    void constructor_price_fail() {
        assertThatIllegalArgumentException().isThrownBy(() -> new Product("상품A", BigDecimal.valueOf(-1)));
    }

    @DisplayName("상품 가격이 0원 이상이면 상품 가격 변경을 성공한다")
    @ValueSource(ints = {0, 1, 1_000})
    @ParameterizedTest
    void changePrice(int price) {
        Product product = new Product("상품A", BigDecimal.ZERO);
        BigDecimal changePrice = BigDecimal.valueOf(price);
        product.changePrice(changePrice);
        assertThat(product.price()).isEqualTo(changePrice);
    }

    @DisplayName("상품 가격이 음수이면 상품 가격 변경을 실패한다")
    @Test
    void changePrice_price_fail() {
        Product product = new Product("상품A", BigDecimal.ZERO);
        BigDecimal changePrice = BigDecimal.valueOf(-1);
        assertThatIllegalArgumentException().isThrownBy(() -> product.changePrice(changePrice));
    }
}
