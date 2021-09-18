package kitchenpos.menus.tobe.domain;

import kitchenpos.common.tobe.application.FakePurgomalumClient;
import kitchenpos.common.tobe.domain.DisplayedName;
import kitchenpos.common.tobe.domain.Profanities;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class MenuGroupTest {
    private Profanities profanities;

    @BeforeEach
    void setUp() {
        profanities = new FakePurgomalumClient();
    }

    @DisplayName("MenuGroup을 생성한다.")
    @Test
    void create() {
        //given
        final DisplayedName name = new DisplayedName("베스트 메뉴", profanities);

        //when
        MenuGroup menuGroup = new MenuGroup(UUID.randomUUID(), name);

        //then
        assertAll(
                () -> assertThat(menuGroup).isNotNull(),
                () -> assertThat(menuGroup.getName()).isEqualTo(name)
        );
    }
}
