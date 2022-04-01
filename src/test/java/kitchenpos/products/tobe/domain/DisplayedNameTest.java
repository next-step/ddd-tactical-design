package kitchenpos.products.tobe.domain;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;

class DisplayedNameTest {

    @ParameterizedTest
    @NullSource
    void notnull(String value) {
        assertThatIllegalArgumentException()
            .isThrownBy(() -> new DisplayedName(value));
    }
}
