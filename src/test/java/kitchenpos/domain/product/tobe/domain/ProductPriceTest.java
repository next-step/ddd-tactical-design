package kitchenpos.domain.product.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

public class ProductPriceTest {

    @DisplayName("ProductPrice을 생성한다")
    @Test
    void constructor() {
        String name = "상품명";
        ProductName productName = new ProductName(name);
        assertThat(productName).isEqualTo(new ProductName(name));
    }

    @DisplayName("ProductPrice의 가격이 음수이면 생성을 실패한다")
    @Test
    void constructor_minus_fail() {
        assertThatIllegalArgumentException().isThrownBy(() -> new ProductPrice(BigDecimal.valueOf(-1)));
    }

    @DisplayName("ProductPrice의 가격이 Null이면 생성을 실패한다")
    @Test
    void constructor_null_fail() {
        assertThatIllegalArgumentException().isThrownBy(() -> new ProductPrice(null));
    }
}
