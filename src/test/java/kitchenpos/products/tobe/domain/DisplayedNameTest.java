package kitchenpos.products.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@DisplayName("상품 이름")
class DisplayedNameTest {

    @DisplayName("상품 이름 생성")
    @Test
    public void createDisplayedName() {
        boolean isProfanity = false;
        assertDoesNotThrow(() -> new DisplayedName("강정 치킨", isProfanity));
    }


    @DisplayName("상품 이름은 null 또는 공백일 수 없다.")
    @NullAndEmptySource
    @ParameterizedTest
    void createEmptyName(String name) {
        boolean isProfanity = false;
        assertThatThrownBy(() -> new DisplayedName(name, isProfanity))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("상품명은 null 이나 공백일 수 없습니다.");
    }

    @DisplayName("'비속어'를 포함할 수 없다.")
    @Test
    void createProfanityName() {
        boolean isProfanity = true;
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new DisplayedName("강정 치킨", isProfanity));
    }

}
