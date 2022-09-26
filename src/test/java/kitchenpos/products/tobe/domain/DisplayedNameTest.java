package kitchenpos.products.tobe.domain;

import kitchenpos.products.application.FakePurgomalumClient;
import kitchenpos.products.infra.PurgomalumClient;
import kitchenpos.products.tobe.domain.exception.NotContainsProfanityException;
import kitchenpos.products.tobe.domain.exception.NotEmptyDisplayedNameException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@DisplayName("상품 이름")
class DisplayedNameTest {

    private PurgomalumClient purgomalumClient;

    @BeforeEach
    void setUp() {
        purgomalumClient = new FakePurgomalumClient();
    }

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
                .isInstanceOf(NotEmptyDisplayedNameException.class);
    }

    @DisplayName("'비속어'를 포함할 수 없다.")
    @Test
    void createProfanityName() {
        boolean isProfanity = true;
        assertThatExceptionOfType(NotContainsProfanityException.class)
                .isThrownBy(() -> new DisplayedName("강정 치킨", isProfanity));
    }

}
