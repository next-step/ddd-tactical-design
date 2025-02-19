package kitchenpos.products.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import kitchenpos.products.application.FakePurgomalumClient;
import kitchenpos.products.infra.PurgomalumClient;
import kitchenpos.products.tobe.domain.exception.DisplayedNameEmptyException;
import kitchenpos.products.tobe.domain.exception.DisplayedNameIncludeProfanityException;
import kitchenpos.products.tobe.domain.model.DisplayedName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

class DisplayedNameTest {

    private PurgomalumClient purgomalumClient;

    @BeforeEach
    void setUp() {
        purgomalumClient = new FakePurgomalumClient();
    }

    @DisplayName("상품 이름을 생성할 수 있다.")
    @ValueSource(strings = {"Valid Name"})
    @ParameterizedTest
    void createDisplayedNameSuccessfully(final String name) {
        DisplayedName displayedName = new DisplayedName(name, purgomalumClient);
        assertEquals("Valid Name", displayedName.getValue());
    }

    @DisplayName("상품 이름은 비어있을 수 없다.")
    @ValueSource(strings = {""})
    @NullSource
    @ParameterizedTest
    void shouldThrowExceptionWhenNameIsEmpty(final String name) {
        assertThrows(DisplayedNameEmptyException.class,
            () -> new DisplayedName(name, purgomalumClient));
    }

    @DisplayName("상품 이름에 비속어, 욕설이 포함되면 등록할 수 없다.")
    @ValueSource(strings = {"비속어", "욕설이 포함된 이름"})
    @ParameterizedTest
    void shouldThrowExceptionWhenNameIncludeProfanity(final String name) {
        assertThrows(DisplayedNameIncludeProfanityException.class,
            () -> new DisplayedName(name, purgomalumClient));
    }
}
