package kitchenpos.products.tobe.domain;

import kitchenpos.products.application.FakePurgomalumClient;
import kitchenpos.products.infra.PurgomalumClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static kitchenpos.products.tobe.domain.ProductFixtures.product;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ProductTest {
    private PurgomalumClient purgomalumClient;

    @BeforeEach
    void setUp() {
        purgomalumClient = new FakePurgomalumClient();
    }

    @DisplayName("상품에 가격을 변경한다.")
    @Test
    void changeProductPrice() {
        Product product = product("후라이드", BigDecimal.valueOf(16000L), purgomalumClient);
        ProductPrice changePrice = new ProductPrice(BigDecimal.valueOf(15000L));

        product.changePrice(changePrice);

        assertThat(product.getPrice()).isEqualTo(changePrice.getPrice());
    }
}
