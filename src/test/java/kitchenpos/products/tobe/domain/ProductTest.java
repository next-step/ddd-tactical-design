package kitchenpos.products.tobe.domain;

import kitchenpos.products.application.FakePurgomalumClient;
import kitchenpos.products.infra.PurgomalumClient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    private final PurgomalumClient purgomalumClient = new FakePurgomalumClient();

    @DisplayName("상품의 가격을 변경할 수 있다.")
    @Test
    void changePrice() {

        // given
        final String menuName = "양념치킨";
        final BigDecimal newPrice = BigDecimal.valueOf(18_000);

        // when
        final Product product = new Product(menuName, purgomalumClient, BigDecimal.valueOf(16_000));
        product.changePrice(newPrice);

        // then
        assertThat(product.getPrice()).isEqualTo(newPrice);
    }
}
