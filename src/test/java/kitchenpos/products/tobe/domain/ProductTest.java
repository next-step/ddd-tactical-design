package kitchenpos.products.tobe.domain;

import kitchenpos.products.infra.DefaultPurgomalumClient;
import kitchenpos.products.infra.PurgomalumClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.web.client.RestTemplateBuilder;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductTest {

    private PurgomalumClient purgomalumClient = new DefaultPurgomalumClient(new RestTemplateBuilder());

    @Test
    @DisplayName("상품을 생성한다.")
    void createProduct() {
        // given
        UUID id = UUID.randomUUID();
        ProductPrice price = new ProductPrice(BigDecimal.valueOf(15000));
        ProductName displayedName = new ProductName("상품 이름", purgomalumClient);

        // when
        Product product = new Product(id, displayedName, price);

        // then
        assertThat(product).isEqualTo(new Product(id, displayedName, price));
    }
}
