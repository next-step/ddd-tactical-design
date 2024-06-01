package kitchenpos.menu.tobe.domain;

import kitchenpos.common.infra.FakePurgomalumClient;
import kitchenpos.menu.tobe.domain.menu.MenuName;
import kitchenpos.menu.tobe.domain.menu.validate.ProfanityValidator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MenuNameTest {
    @Test
    @DisplayName("메뉴명이 null이라면 예외가 발생한다.")
    void test1() {
        ProfanityValidator profanityValidator = new ProfanityValidator(new FakePurgomalumClient(false));

        Assertions.assertThatThrownBy(() -> new MenuName(null, profanityValidator))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("메뉴명이 비속어가 포함되어 있다면 예외가 발생한다.")
    void test2() {
        ProfanityValidator profanityValidator = new ProfanityValidator(new FakePurgomalumClient(true));

        Assertions.assertThatThrownBy(() -> new MenuName("ㅅㅂ", profanityValidator))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("메뉴명을 정상적으로 생성할 수 있다.")
    void test3() {
        ProfanityValidator profanityValidator = new ProfanityValidator(new FakePurgomalumClient(false));
        MenuName menuName = new MenuName("메뉴", profanityValidator);

        Assertions.assertThat(menuName).isNotNull();
    }
}