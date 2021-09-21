package kitchenpos.common.domain;

import kitchenpos.products.application.FakePurgomalumClient;
import kitchenpos.products.infra.PurgomalumClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class NameTest {
    private PurgomalumClient purgomalumClient;

    @BeforeEach
    void setUp() {
        purgomalumClient = new FakePurgomalumClient();
    }

    @DisplayName("이름을 설정한다.")
    @Test
    void create() {
        final String value = "치킨";
        final Name name = new Name(value, purgomalumClient);

        assertThat(name.getName()).isEqualTo(value);
    }

    @DisplayName("이름이 반값이거나 욕설이 포함된 경우 등록할 수 없다.")
    @ValueSource(strings = {"비속어", "욕설"})
    @ParameterizedTest
    void create(final String value) {
        assertThatThrownBy(() -> new Name(value, purgomalumClient))
            .isInstanceOf(IllegalArgumentException.class);
    }
}
