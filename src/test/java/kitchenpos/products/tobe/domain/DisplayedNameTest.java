package kitchenpos.products.tobe.domain;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import kitchenpos.products.domain.ProfanityValidator;
import kitchenpos.products.tobe.fake.FakeProfanityValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class DisplayedNameTest {

    private ProfanityValidator profanity;

    @BeforeEach
    void setUp() {
        profanity = FakeProfanityValidator.from("비속어");
    }

    @Test
    @DisplayName("이름이 비어있을 수는 없다.")
    void name_is_not_null() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> DisplayedName.of(null, profanity));
    }

    @Test
    @DisplayName("비속어는 비어있을 수는 없다.")
    void profanity_is_not_null() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> DisplayedName.of("이름", null));
    }

    @Test
    @DisplayName("욕설이 포함될수 없다.")
    void name_is_has_no_profanitys() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> DisplayedName.of("비속어", profanity));
    }

}
