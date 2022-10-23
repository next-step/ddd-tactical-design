package kitchenpos.common.name;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatNoException;

import kitchenpos.common.profanity.FakeProfanityDetectService;
import kitchenpos.common.profanity.domain.ProfanityDetectService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class NameFactoryTest {

    private final ProfanityDetectService profanityDetectClient = new FakeProfanityDetectService();

    // SUT

    private final NameFactory nameFactory = new NameFactory(
        profanityDetectClient
    );

    @DisplayName("올바른 이름으로 생성할 수 있다.")
    @ValueSource(strings = {
        "firm", "scorn", "veil", "friendly", "amount",
        "fence", "price", "box", "private", "easy",
    })
    @ParameterizedTest
    void qbhvyypp(final String name) {
        assertThatNoException().isThrownBy(() -> this.nameFactory.create(name));
    }

    @DisplayName("이름은 비어있을 수 없다.")
    @NullAndEmptySource
    @ParameterizedTest
    void rcdkawwe(final String name) {
        assertThatIllegalArgumentException().isThrownBy(() -> this.nameFactory.create(name));
    }

    @DisplayName("이름은 공백으로만 구성될 수 없다.")
    @ValueSource(strings = {"   ", "\t"})
    @ParameterizedTest
    void ljddwkyc(final String name) {
        assertThatIllegalArgumentException().isThrownBy(() -> this.nameFactory.create(name));
    }

    @DisplayName("이름은 비속어를 포함할 수 없다.")
    @ValueSource(strings = {"비속어", "욕설"})
    @ParameterizedTest
    void kyphttxj(final String name) {
        assertThatIllegalArgumentException().isThrownBy(() -> this.nameFactory.create(name));
    }
}
