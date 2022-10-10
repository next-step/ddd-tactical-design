package kitchenpos.products.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

class ProfanityTest {

    @DisplayName("Profanity는 비어있을 수 없습니다.")
    void profanity() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> new Profanity(null));
    }

    @DisplayName("Profanity의 단어는 비어 있을 수 없습니다.")
    @ParameterizedTest
    @NullAndEmptySource
    void from(String word) {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> Profanity.from(word));
    }

    @DisplayName("욕설이 포함되어 있는지 확인")
    @Test
    void isHas() {
        Profanity profanity = new Profanity(Set.of("욕설"));

        assertAll(
            () -> assertThat(profanity.isHas("욕설")).isTrue(),
            () -> assertThat(profanity.isHas("안욕설")).isFalse()
        );
    }


}
