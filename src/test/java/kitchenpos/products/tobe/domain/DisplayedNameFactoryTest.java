package kitchenpos.products.tobe.domain;

import kitchenpos.products.application.FakePurgomalumClient;
import kitchenpos.products.infra.PurgomalumClient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DisplayedNameFactoryTest {

    @DisplayName("`DisplayedName`에는 `name`이 필수고, `profanity`가 포함될 수 없다.")
    @NullSource
    @ValueSource(strings = {"비속어", "욕설이 포함된 이름"})
    @ParameterizedTest
    void create(final String name) {
        final PurgomalumClient purgomalumClient = new FakePurgomalumClient();
        final DisplayedNameFactory displayedNameFactory = new DisplayedNameFactory(purgomalumClient);

        assertThatThrownBy(() -> displayedNameFactory.create(name)).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이름은 필수고, 비속어가 포함될 수 없습니다");
    }
}
