package kitchenpos.menus.tobe.domain.menu.domain;

import kitchenpos.common.tobe.domain.Name;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class NameTest {

    @DisplayName("정상적으로 생성되는 것 테스트.")
    @Test
    void create() {
        Name result = new Name("상품명");
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("상품명");
    }
}