package kitchenpos.products.tobe.domain;

import kitchenpos.products.application.FakePurgomalumClient;
import kitchenpos.products.infra.PurgomalumClient;
import kitchenpos.products.tobe.domain.exception.InvalidProductNameException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ProductNameTest {

    private PurgomalumClient purgomalum;

    @BeforeEach
    void setUp() {
        purgomalum = new FakePurgomalumClient();
    }

    @DisplayName("상품명에 null이 입력되면 예외가 발생한다")
    @NullSource
    @ParameterizedTest
    void notNull(String name) {
        assertThatThrownBy(() -> new ProductName(name, purgomalum))
                .isInstanceOf(NullPointerException.class);
    }

    @DisplayName("상품명에 비속어가 포함되면 예외가 발생한다")
    @ValueSource(strings = {"비속어", "욕설"})
    @ParameterizedTest
    void validateName(String name) {
        assertThatThrownBy(() -> new ProductName(name, purgomalum))
                .isInstanceOf(InvalidProductNameException.class);
    }
}
