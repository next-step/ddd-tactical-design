package kitchenpos.product.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class NameTest {
    @DisplayName("이름은 비어 있지 않으면 된다")
    @Test
    void test1() {
        final Name name = new Name("name");

        assertThat(name.getValue()).isEqualTo("name");
    }

    @DisplayName("이름은 비어 있으면 안된다")
    @Test
    void test2() {
        assertThatExceptionOfType(IllegalNewProductNameException.class)
                .isThrownBy(() -> new Name(null));
    }
}
