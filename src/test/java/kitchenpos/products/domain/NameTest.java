package kitchenpos.products.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import kitchenpos.products.application.FakePurgomalumClient;
import kitchenpos.products.infra.PurgomalumClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;

class NameTest {

    private PurgomalumClient purgomalumClient;

    @BeforeEach
    void setup() {
        purgomalumClient = new FakePurgomalumClient();
    }

    @Test
    @DisplayName("이름 생성")
    void constructor() {
        final Name expected = new Name("product Name");
        assertThat(expected).isNotNull();
    }

    @ParameterizedTest
    @DisplayName("이름은 필수이다")
    @NullSource
    void create_with_null(final String name) {
        assertThatThrownBy(() -> new Name(name))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("비속어를 포함 할 수 없다")
    void slang_test() {
        final Name expected = new Name("비속어");
        assertThatThrownBy(() -> expected.verifySlang(purgomalumClient))
            .isInstanceOf(IllegalArgumentException.class);
    }
}
