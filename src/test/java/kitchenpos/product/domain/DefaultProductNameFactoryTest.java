package kitchenpos.product.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doReturn;

import kitchenpos.product.application.exception.ContainsProfanityException;
import kitchenpos.product.application.port.out.ProductPurgomalumChecker;
import kitchenpos.support.vo.Name;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(ReplaceUnderscores.class)
class DefaultProductNameFactoryTest {

    @Mock
    private ProductPurgomalumChecker mockChecker;

    private ProductNameFactory factory;

    @BeforeEach
    void setUp() {
        factory = new DefaultProductNameFactory(mockChecker);
    }

    @ParameterizedTest
    @NullSource
    void create_invalid_parameters_이름후보가_없으면_예외를_발생시킨다(final Name value) {

        // when & then
        assertThatThrownBy(() -> factory.create(value))
            .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @ValueSource(strings = "비속어포함된이름")
    void create_이름후보에_비속어가_포함되어있으면_예외를_발생시킨다(final String value) {
        // given
        final Name profanityName = new Name(value);
        doReturn(true)
            .when(mockChecker)
            .containsProfanity(profanityName);

        // when & then
        assertThatThrownBy(() -> factory.create(profanityName))
            .isExactlyInstanceOf(ContainsProfanityException.class);
    }


    @ParameterizedTest
    @ValueSource(strings = "dummy")
    void create_이름후보의_값을_가지고_음식이름을_만들어서_반환한다(final String value) {
        // given
        final Name name = new Name(value);

        // when
        final ProductName actual = factory.create(name);

        // then
        assertThat(actual).isNotNull();
        assertThat(actual)
            .extracting("value")
            .isEqualTo(value);
    }
}