package kitchenpos.products.tobe.domain;

import kitchenpos.products.application.FakePurgomalumClient;
import kitchenpos.products.infra.PurgomalumClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

class ProductTest {
    private PurgomalumClient purgomalumClient;

    @BeforeEach
    void setUp() {
        purgomalumClient = new FakePurgomalumClient();
    }

    @DisplayName("이름과 가격으로 상품을 생성한다")
    @Test
    void create() {
        assertThatCode(() ->
                new Product(purgomalumClient, "짜장면", BigDecimal.valueOf(6_000)))
                .doesNotThrowAnyException();
    }

    @DisplayName("상품 가격을 변경한다")
    @Test
    void changePrice() {
        // given
        Product product = new Product(purgomalumClient, "짜장면", BigDecimal.valueOf(6_000));

        // when
        product.changePrice(BigDecimal.valueOf(7_000));

        // then
        assertThat(product.getPrice()).isEqualTo(new ProductPrice(7_000));
    }
}
