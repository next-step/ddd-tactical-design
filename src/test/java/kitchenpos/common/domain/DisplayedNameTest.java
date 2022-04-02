package kitchenpos.common.domain;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

class DisplayedNameTest {

    @ParameterizedTest
    @NullAndEmptySource
    void notnullAndNotEmpty(String value) {
        assertThatIllegalArgumentException()
            .isThrownBy(() -> new DisplayedName(value));
    }
}
