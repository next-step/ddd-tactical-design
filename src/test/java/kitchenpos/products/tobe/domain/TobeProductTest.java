package kitchenpos.products.tobe.domain;

import kitchenpos.products.application.FakePurgomalumClient;
import kitchenpos.products.infra.PurgomalumClient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class TobeProductTest {

    private final PurgomalumClient purgomalumClient = new FakePurgomalumClient();

    @DisplayName("상품의 가격을 변경한다.")
    @Test
    void changePrice01() {
        TobeProduct product = new TobeProduct(UUID.randomUUID(), new ProductName("후라이드 치킨", purgomalumClient),
                                              new ProductPrice(BigDecimal.valueOf(10000)));

        product.changePrice(BigDecimal.valueOf(15000));

        assertThat(product.getPrice()).isEqualTo(new ProductPrice(BigDecimal.valueOf(15000)));
    }
}
