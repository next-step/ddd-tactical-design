package kitchenpos.products.tobe.domain;

import kitchenpos.products.application.FakePurgomalumClient;
import kitchenpos.products.infra.PurgomalumClient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ProductNameTest {
    private final PurgomalumClient purgomalumClient = new FakePurgomalumClient();

    @DisplayName("상품 이름을 생성한다.")
    @Test
    void create01() {
        ProductName name = new ProductName("후라이드 치킨", purgomalumClient);

        assertThat(name).isEqualTo(new ProductName("후라이드 치킨", purgomalumClient));
    }

    @DisplayName("상품 이름은 비어있을 수 없다.")
    @Test
    void create02() {
        assertThatThrownBy(() -> new ProductName(null, purgomalumClient))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("상품의 이름은 비어 있을 수 없습니다.");
    }

    @DisplayName("상품 이름에는 비속어가 있을 수 없다.")
    @Test
    void create03() {
        assertThatThrownBy(() -> new ProductName("욕설 치킨", purgomalumClient))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("이름에는 비속어가 포함될 수 없습니다.");
    }
}
