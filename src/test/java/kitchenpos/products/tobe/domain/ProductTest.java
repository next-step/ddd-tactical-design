package kitchenpos.products.tobe.domain;

import kitchenpos.products.application.FakePurgomalumClient;
import kitchenpos.products.infra.PurgomalumClient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    @DisplayName("상품을 등록해보자")
    @ParameterizedTest
    @CsvSource(value = {"후라이드:10000", "양념치킨:15000"}, delimiter = ':')
    void create(String name, int price) {
        PurgomalumClient purgomalumClient = new FakePurgomalumClient();
        Product product = new Product(purgomalumClient, name, BigDecimal.valueOf(price));

        assertAll(
                () -> assertThat(product).isNotNull(),
                () -> assertThat(product.getId()).isNotNull(),
                () -> assertThat(product.getName()).isEqualTo(name),
                () -> assertThat(product.getPrice()).isEqualTo(BigDecimal.valueOf(price))
        );
    }
}
