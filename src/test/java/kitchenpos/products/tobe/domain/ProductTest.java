package kitchenpos.products.tobe.domain;

import kitchenpos.products.application.FakePurgomalumClient;
import kitchenpos.products.infra.PurgomalumClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class ProductTest {
    private PurgomalumClient purgomalumClient;

    @BeforeEach
    void setUp() {
        purgomalumClient = new FakePurgomalumClient();
    }

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