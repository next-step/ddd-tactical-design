package kitchenpos.common.tobe.domain;

import kitchenpos.common.tobe.application.FakePurgomalumClient;
import kitchenpos.common.tobe.exception.WrongDisplayedNameException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static kitchenpos.common.tobe.exception.WrongDisplayedNameException.DISPLAYED_NAME_SHOULD_NOT_BE_EMPTY;
import static kitchenpos.common.tobe.exception.WrongDisplayedNameException.DISPLAYED_NAME_SHOULD_NOT_CONTAIN_PROFANITY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertAll;

class DisplayedNameTest {
    private Profanities profanities;

    @BeforeEach
    void setUp() {
        profanities = new FakePurgomalumClient();
    }

    @DisplayName("상품 이름을 만들 수 있다.")
    @Test
    void create() {
        //given
        String name = "후라이드";

        //when
        final DisplayedName displayedName = new DisplayedName(name, profanities);

        //then
        assertAll(
                () -> assertThat(displayedName).isNotNull(),
                () -> assertThat(displayedName.getName()).isEqualTo(name)
        );
    }

    @DisplayName("상품 이름은 비워둘 수 없다.")
    @NullSource
    @ParameterizedTest
    void create_fail_empty__name(String name) {
        assertThatExceptionOfType(WrongDisplayedNameException.class)
                .isThrownBy(() -> new DisplayedName(name, profanities))
                .withMessage(DISPLAYED_NAME_SHOULD_NOT_BE_EMPTY);
    }

    @DisplayName("상품 이름은 비속어가 포함 수 없다.")
    @ValueSource(strings = {"비속어", "욕설이 포함된 이름"})
    @ParameterizedTest
    void create_fail_empty_or_purgomalum_name(String name) {
        assertThatExceptionOfType(WrongDisplayedNameException.class)
                .isThrownBy(() -> new DisplayedName(name, profanities))
                .withMessage(DISPLAYED_NAME_SHOULD_NOT_CONTAIN_PROFANITY);
    }

    @Test
    void 동등성() {
        DisplayedName name1 = new DisplayedName("치킨", profanities);
        DisplayedName name2 = new DisplayedName("치킨", profanities);
        assertThat(name1).isEqualTo(name2);
    }
}
