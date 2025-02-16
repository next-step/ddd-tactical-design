package kitchenpos.products.tobe.application;

import kitchenpos.products.application.FakePurgomalumClient;
import kitchenpos.products.infra.PurgomalumClient;
import kitchenpos.products.tobe.domain.ProductName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.assertj.core.api.Assertions.*;

class ProductNameServiceTest {

    private PurgomalumClient purgomalumClient;

    @BeforeEach
    void setUp() {
        purgomalumClient = new FakePurgomalumClient();
    }

    @DisplayName("상품명 정상 생성")
    @Test
    void success_create() {
        assertThatNoException()
                .isThrownBy(() -> new ProductName("후라이드", purgomalumClient));
    }

    @DisplayName("상품명으로 NULL, 빈 값으로 들어갈 수 없다.")
    @NullAndEmptySource
    @ParameterizedTest
    void fail_invalid_name(String name) {
        assertThatThrownBy(() -> new ProductName(name, purgomalumClient))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("상품명으로 욕설이 들어갈 수 없다.")
    @Test
    void fail_contains_profanity() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> new ProductName("욕설", purgomalumClient));
    }
}
