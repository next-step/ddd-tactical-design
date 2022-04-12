package kitchenpos.products.tobe.domain;

import kitchenpos.products.application.FakePurgomalumClient;
import kitchenpos.products.infra.PurgomalumClient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class ProductNameTest {

    @DisplayName("정상적으로 상품을 생성해보자")
    @ParameterizedTest
    @ValueSource(strings = {"후라이드치킨", "양념치킨"})
    void createProduct(String name) {
        PurgomalumClient purgomalumClient = new FakePurgomalumClient();
        ProductName productName = new ProductName(purgomalumClient, name);

        assertAll(
                () -> assertThat(productName).isNotNull(),
                () -> assertThat(productName.getName()).isEqualTo(name)
        );
    }

    @DisplayName("상품이름에 null은 불가능하다")
    @Test
    void invalidProductName() {
        PurgomalumClient purgomalumClient = new FakePurgomalumClient();

        assertThatThrownBy(
                () -> new ProductName(purgomalumClient, null)
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("상품이름이 비속어가 될수 없다.")
    @ParameterizedTest
    @ValueSource(strings = {"비속어", "욕설"})
    void slangProductName(String name) {
        PurgomalumClient purgomalumClient = new FakePurgomalumClient();

        assertThatThrownBy(
                () -> new ProductName(purgomalumClient, name)
        ).isInstanceOf(IllegalArgumentException.class);
    }
}
