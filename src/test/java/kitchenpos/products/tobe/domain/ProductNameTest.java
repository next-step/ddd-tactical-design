package kitchenpos.products.tobe.domain;

import kitchenpos.products.application.FakeProductPurgomalumClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ProductNameTest {

    private FakeProductPurgomalumClient purgomalumClient;

    @BeforeEach
    void setUp() {
        purgomalumClient = new FakeProductPurgomalumClient();
    }

    @DisplayName("이름생성")
    @Test
    void 이름생성() {
        // when
        ProductName productName = new ProductName("치킨", purgomalumClient);
        // that
        assertThat(productName.getName()).isEqualTo("치킨");
    }

    @DisplayName("이름에는 비속어가 포함될 수 없다.")
    @Test
    void 이름생성_욕설포함() {
        // when then
        assertThatThrownBy(() -> new ProductName("욕설", purgomalumClient))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
