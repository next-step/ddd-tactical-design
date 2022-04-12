package kitchenpos.products.tobe.domain;

import kitchenpos.products.application.FakePurgomalumClient;
import kitchenpos.products.infra.PurgomalumClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ProductNameTest {
    private PurgomalumClient purgomalumClient;

    @BeforeEach
    void setUp() {
        purgomalumClient = new FakePurgomalumClient();
    }

    @Test
    void create() {
        assertThatCode(() -> new ProductName("짜장면", purgomalumClient))
                .doesNotThrowAnyException();
    }

    @ParameterizedTest
    @NullSource
    @EmptySource
    void createInvalidName(String name) {
        assertThatThrownBy(() -> new ProductName(name, purgomalumClient))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이름은 빈 값이 아니어야 합니다. 입력 값 : " + name);
    }

    @DisplayName("상품 이름에는 비속어가 포함되지 않아야 한다")
    @Test
    void createProfanity() {
        assertThatThrownBy(() -> new ProductName("비속어", purgomalumClient))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이름에는 비속어가 포함되지 않아야 합니다. 입력 값 : 비속어");
    }
}