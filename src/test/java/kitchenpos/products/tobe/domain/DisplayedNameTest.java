package kitchenpos.products.tobe.domain;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;

class DisplayedNameTest {

    @ParameterizedTest
    @NullAndEmptySource
    void notnullAndNotEmpty(String value) {
        assertThatIllegalArgumentException()
            .isThrownBy(() -> new DisplayedName(value));
    }
}
