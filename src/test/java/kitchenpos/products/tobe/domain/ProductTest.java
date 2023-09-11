package kitchenpos.products.tobe.domain;

import kitchenpos.products.tobe.application.FakePurgomalumClient;
import kitchenpos.products.tobe.infra.PurgomalumClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ProductTest {
    private PurgomalumClient purgomalumClient;

    @BeforeEach
    void setUp() {
        purgomalumClient = new FakePurgomalumClient();
    }

    @Test
    @DisplayName("상품 등록에 성공한다.")
    void create_product() {
        String name = "후라이드";
        BigDecimal price = new BigDecimal(16000L);
        Product actual = new Product(name, purgomalumClient, price);

        assertThat(actual.getId()).isNotNull();
        assertThat(actual.getName().getName()).isEqualTo(name);
        assertThat(actual.getPrice().getPrice()).isEqualTo(price);
    }

    @ParameterizedTest
    @DisplayName("상품의 가격이 올바르지 않으면 등록할 수 없다.")
    @ValueSource(strings = "-1000")
    @NullSource
    void 상품_생성_성공(final BigDecimal price) {
        String name = "후라이드";
        assertThatThrownBy(() -> new Product(name, purgomalumClient, price))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @DisplayName("상품의 이름이 올바르지 않으면 등록할 수 없다.")
    @ValueSource(strings = {"비속어", "욕설이 포함된 이름"})
    @NullSource
    void create(final String name) {
        BigDecimal price = new BigDecimal(16000L);

        assertThatThrownBy(() -> new Product(name, purgomalumClient, price))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
