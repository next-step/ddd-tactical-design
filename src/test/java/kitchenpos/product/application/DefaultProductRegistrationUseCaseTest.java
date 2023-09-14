package kitchenpos.product.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

import kitchenpos.product.Fixtures;
import kitchenpos.product.application.exception.ContainsProfanityException;
import kitchenpos.product.application.port.in.ProductRegistrationUseCase;
import kitchenpos.product.application.port.out.ProductNewRepository;
import kitchenpos.product.domain.Name;
import kitchenpos.product.domain.ProductNameAccessor;
import kitchenpos.product.domain.ProductNameFactory;
import kitchenpos.product.domain.ProductPrice;
import kitchenpos.product.fakerepository.ProductNewFakeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayNameGeneration(ReplaceUnderscores.class)
@ExtendWith(MockitoExtension.class)
class DefaultProductRegistrationUseCaseTest {

    @Mock
    private ProductNameFactory mockFactory;

    private ProductNewRepository repository;

    private ProductRegistrationUseCase useCase;

    @BeforeEach
    void setUp() {
        repository = new ProductNewFakeRepository();

        useCase = new DefaultProductRegistrationUseCase(mockFactory, repository);
    }

    @ParameterizedTest
    @NullSource
    void register_invalid_parameters_음식이름_후보가_null이면_예외를_발생시킨다(final Name value) {

        // when & then
        assertThatThrownBy(() -> useCase.register(value, Fixtures.VALID_PRODUCT_PRICE))
            .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @NullSource
    void register_invalid_parameters_음식가격이_null이면_예외를_발생시킨다(final ProductPrice value) {

        // when & then
        assertThatThrownBy(() -> useCase.register(Fixtures.VALID_NAME, value))
            .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @ValueSource(strings = "비속어가포함된이름")
    void register_음식이름후보에_비속어가_포함되어_있으면_예외를_발생시킨다(final String value) {
        // given
        final Name profanityName = new Name(value);
        doThrow(ContainsProfanityException.class)
            .when(mockFactory)
            .create(profanityName);

        // when & then
        assertThatThrownBy(() -> useCase.register(profanityName, Fixtures.VALID_PRODUCT_PRICE))
            .isExactlyInstanceOf(ContainsProfanityException.class);
    }


    @Test
    void register_product를_생성한_후_저장한다() {
        // given
        doReturn(ProductNameAccessor.create(Fixtures.VALID_NAME))
            .when(mockFactory)
            .create(Fixtures.VALID_NAME);

        // when
        final ProductDTO actual = useCase.register(Fixtures.VALID_NAME,
            Fixtures.VALID_PRODUCT_PRICE);

        // then
        assertThat(repository.findById(actual.getId())).isNotNull();
    }
}