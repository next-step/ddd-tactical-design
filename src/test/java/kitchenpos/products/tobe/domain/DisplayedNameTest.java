package kitchenpos.products.tobe.domain;

import kitchenpos.products.application.FakePurgomalumClient;
import kitchenpos.products.tobe.exception.ContainsProfanityException;
import kitchenpos.products.tobe.exception.DisplayedNameEmptyException;
import kitchenpos.products.tobe.infra.DefaultProfanityValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class DisplayedNameTest {

    private ProfanityValidator profanityValidator;

    @BeforeEach
    void setUp() {
        var purgomalumClient = new FakePurgomalumClient();
        profanityValidator = new DefaultProfanityValidator(purgomalumClient);
    }

    @DisplayName("상품의 이름에 욕설이 포함되면 생성할 수 없다.")
    @ValueSource(strings = {"비속어", "욕설이 포함된 이름"})
    @ParameterizedTest
    void containsProfanity(final String name) {
        assertThatThrownBy(() -> DisplayedName.of(name, profanityValidator))
                .isInstanceOf(ContainsProfanityException.class);
    }

    @DisplayName("상품의 이름이 비어있으면 생성할 수 없다.")
    @NullSource
    @ParameterizedTest
    void emptyPrice(final String name) {
        assertThatThrownBy(() -> DisplayedName.of(name, profanityValidator))
                .isInstanceOf(DisplayedNameEmptyException.class);
    }

}
