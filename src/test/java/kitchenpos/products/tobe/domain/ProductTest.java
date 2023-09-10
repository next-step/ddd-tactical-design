package kitchenpos.products.tobe.domain;

import kitchenpos.common.FakeProfanityPolicy;
import kitchenpos.common.domain.ProfanityPolicy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;

@DisplayName("상품")
@SpringBootTest
class ProductTest {

    private ProfanityPolicy profanityPolicy;
    private static final String NAME = "상품";

    @BeforeEach
    void setUp() {
        profanityPolicy = new FakeProfanityPolicy();
    }

    @DisplayName("[성공] 상품을 생성한다")
    @Test
    void create() {
        assertThatNoException().isThrownBy(
                () -> Product.of(NAME, BigDecimal.TEN, profanityPolicy)
        );
    }

    @DisplayName("[성공] 상품의 가격을 바꾼다.")
    @Test
    void changePrice1() {
        //given
        Product product = Product.of(NAME, BigDecimal.TEN, profanityPolicy);
        //when
        product.changePrice(new ProductPrice(1000));
        //then
        assertThat(product.getPrice())
                .isEqualTo(new ProductPrice(1000));
    }
}
