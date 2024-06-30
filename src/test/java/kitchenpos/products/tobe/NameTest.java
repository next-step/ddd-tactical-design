package kitchenpos.products.tobe;


import kitchenpos.infra.PurgomalumClient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class NameTest {

    @DisplayName("Name 생성")
    @Test
    void createName_successTest() {

        Name name = new Name("Name");
        assertThat(name).isNotNull();
    }

    @DisplayName("Name이 null이면 생성 실패한다")
    @Test
    void createName_failTest() {
        assertThatThrownBy(() -> new Name(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("name은 필수 입력값입니다.");
    }

    @DisplayName("Name에 비속어가 포함되어 있으면 생성 실패한다")
    @Test
    void createName_failWhenHasProfanity() {

        PurgomalumClient purgomalumClient = "비속어"::equals;

        assertThatThrownBy(() -> new Name("비속어", purgomalumClient))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("비속어가 포함되어 있습니다.");
    }
}