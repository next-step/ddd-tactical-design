package kitchenpos.products.tobe.domain;

import kitchenpos.products.application.FakePurgomalumClient;
import kitchenpos.products.infra.PurgomalumClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ProductNameTest {

    private PurgomalumClient purgomalumClient;

    @BeforeEach
    void setUp() {
        this.purgomalumClient = new FakePurgomalumClient();
    }

    @DisplayName("성공적으로 상품이름이 생성된다.")
    @Test
    void success() {
        final ProductName productName = new ProductName("치킨", purgomalumClient);

        assertThat(productName).isEqualTo(new ProductName("치킨", purgomalumClient));
    }

    @DisplayName("상품 이름이 존재하지 않으면 에러를 반환한다.")
    @ParameterizedTest
    @NullSource
    void emptyProductNameException(String name) {
        assertThatThrownBy(() -> new ProductName(name, purgomalumClient))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("상품 이름 값이 존재하지 않습니다.");
    }

    @DisplayName("상품 이름에 비속어가 존재하면 에러를 반환한다.")
    @Test
    void containsPurgomalumException() {
        final String name = "비속어";

        assertThatThrownBy(() -> new ProductName(name, purgomalumClient))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("상품 이름에 비속어가 존재합니다.");
    }

}
