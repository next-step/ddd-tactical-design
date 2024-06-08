package kitchenpos.menus.tobe.domain;

import kitchenpos.products.application.FakePurgomalumClient;
import kitchenpos.products.infra.PurgomalumClient;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DisplayNameTest {

    private PurgomalumClient purgomalumClient;

    @BeforeEach
    void setUp() {
        purgomalumClient = new FakePurgomalumClient();
    }

    @Test
    @org.junit.jupiter.api.DisplayName("메뉴 이름이 비어있으면 예외가 발생한다.")
    void validateNameWhenEmpty() {
        // when
        // then
        Assertions.assertThatIllegalArgumentException().isThrownBy(() -> DisplayName.of("", purgomalumClient))
                .withMessage("메뉴 이름은 필수값입니다.");
    }

    @Test
    @org.junit.jupiter.api.DisplayName("메뉴 이름에 비속어가 포함되어 있으면 예외가 발생한다.")
    void validateNameWhenContainsProfanity() {
        // when
        // then
        Assertions.assertThatIllegalArgumentException().isThrownBy(() -> DisplayName.of("비속어", purgomalumClient))
                .withMessage("비속어가 포함된 메뉴이름은 등록할 수 없습니다.");
    }
}
