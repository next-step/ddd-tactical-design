package kitchenpos.products.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DisplayedNameTest {

    @Test
    @DisplayName("상점에 노출할 이름을 지을수 있다.")
    void name() {
        DisplayedName displayedName1 = new DisplayedName("상점 제목");
        DisplayedName displayedName2 = new DisplayedName("상점 제목");

        assertThat(displayedName1).isEqualTo(displayedName2);
    }

    @Test
    @DisplayName("상점에 노출할 이름은 비어있을 수 없다.")
    void name_empty() {
        assertThatThrownBy(() -> new DisplayedName(""))
                .isInstanceOf(IllegalArgumentException.class);
    }
}