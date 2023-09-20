package kitchenpos.products.tobe.domain;

import kitchenpos.products.domain.PurgomalumClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class DisplayedNameTest {

    private final PurgomalumClient purgomalumClient = new FakePurgomalumClient();

    @ParameterizedTest
    @NullAndEmptySource
    void 이름은_없거나_빈_값일_수_없다(String nullAndEmpty) {
        assertThatThrownBy(() -> new DisplayedName(nullAndEmpty, purgomalumClient))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(String.format("이름은 없거나 빈 값일 수 없습니다. 현재 값: %s", nullAndEmpty));
    }

    @ParameterizedTest
    @ValueSource(strings = {"꿀", "치킨"})
    void 이름은_1글자_이상이다(String name) {
        assertDoesNotThrow(() -> new DisplayedName(name, purgomalumClient));
    }

    @Test
    void 이름은_욕설을_포함할_수_없다() {
        String slang = "치킨 fxxk";

        assertThatThrownBy(() -> new DisplayedName(slang, purgomalumClient))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(String.format("이름은 욕설을 포함할 수 없습니다. 현재 값: %s", slang));
    }

    @Test
    void DisplayedName_동등성_비교() {
        DisplayedName actual = new DisplayedName("꿀", purgomalumClient);

        assertThat(actual.equals(actual)).isTrue();

        assertThat(actual.equals(null)).isFalse();
        assertThat(actual.equals(5)).isFalse();

        assertThat(actual.equals(new DisplayedName("꿀", purgomalumClient))).isTrue();
        assertThat(actual.equals(new DisplayedName("치킨", purgomalumClient))).isFalse();
    }

    @Test
    void DisplayedName_hashCode() {
        DisplayedName actual = new DisplayedName("꿀", purgomalumClient);

        assertThat(actual.hashCode()).isEqualTo(new DisplayedName("꿀", purgomalumClient).hashCode());
        assertThat(actual.hashCode()).isNotEqualTo(new DisplayedName("치킨", purgomalumClient).hashCode());
    }
}
