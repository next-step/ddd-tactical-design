package kitchenpos.products.tobe.domain;

import kitchenpos.share.domain.FakeProfanities;
import kitchenpos.shared.domain.Profanities;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ProfanitiesTest {

    private Profanities profanities;

    @BeforeEach
    void setUp() {
        profanities = new FakeProfanities("비속어");
    }

    @DisplayName("비속어가 있으면 true 를 리턴한다.")
    @Test
    void case_1() {
        // given
        String text = "비속어";

        // when
        boolean result = profanities.contains(text);

        // then
        assertThat(result).isTrue();
    }

    @DisplayName("비속어가 없으면 false 를 리턴한다.")
    @Test
    void case_2() {
        // given
        String text = "후라이드";

        // when
        boolean result = profanities.contains(text);

        // then
        assertThat(result).isFalse();
    }
}
