package kitchenpos.menus.tobe.domain.menu.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static kitchenpos.menus.TobeFixtures.twoChickens;
import static org.assertj.core.api.Assertions.assertThat;

class MenuGroupTest {

    @DisplayName("정상적으로 생성되는 것 테스트.")
    @Test
    void create() {
        MenuGroup result = twoChickens();
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("두마리메뉴");
    }
}