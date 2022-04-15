package kitchenpos.products.tobe.domain;

import kitchenpos.products.application.FakePurgomalumClient;
import kitchenpos.products.infra.PurgomalumClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ProductNameTest {
    private PurgomalumClient purgomalumClient;

    @BeforeEach
    void setUp() {
        purgomalumClient = new FakePurgomalumClient();
    }

    @DisplayName("상품 이름을 생성한다")
    @Test
    void create() {
        assertThatCode(() -> new ProductName(purgomalumClient, "짜장면"))
                .doesNotThrowAnyException();
    }

    @DisplayName("상품 이름은 비어있지 않아야 한다")
    @ParameterizedTest
    @NullAndEmptySource
    void createInvalidName(String name) {
        assertThatThrownBy(() -> new ProductName(purgomalumClient, name))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이름은 빈 값이 아니어야 합니다. 입력 값 : " + name);
    }

    @DisplayName("상품 이름에는 비속어가 포함되지 않아야 한다")
    @Test
    void createProfanity() {
        assertThatThrownBy(() -> new ProductName(purgomalumClient, "비속어"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이름에는 비속어가 포함되지 않아야 합니다. 입력 값 : 비속어");
    }
}
