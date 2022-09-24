package kitchenpos.products.tobe.domain;

import kitchenpos.products.application.FakePurgomalumClient;
import kitchenpos.products.infra.PurgomalumClient;
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
        assertDoesNotThrow(() -> new DisplayedName(purgomalumClient, "강정 치킨"));
    }


    @DisplayName("상품 이름은 null 또는 공백일 수 없다.")
    @NullAndEmptySource
    @ParameterizedTest
    void createEmptyName(String name) {
        assertThatThrownBy(() -> new DisplayedName(purgomalumClient, name))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("'비속어'를 포함할 수 없다.")
    @ParameterizedTest
    @ValueSource(strings = {"비속어", "욕설"})
    void createProfanityName(String profanity) {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new DisplayedName(purgomalumClient, profanity));
    }

}