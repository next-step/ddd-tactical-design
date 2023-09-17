package kitchenpos.product.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import java.util.UUID;
import kitchenpos.product.Fixtures;
import kitchenpos.product.application.exception.NotExistProductException;
import kitchenpos.product.application.port.in.ProductPriceChangeUseCase;
import kitchenpos.product.application.port.out.ProductNewRepository;
import kitchenpos.product.application.port.out.ProductPriceChangeEventPublisher;
import kitchenpos.product.domain.ProductNew;
import kitchenpos.product.domain.ProductPrice;
import kitchenpos.product.fakerepository.ProductNewFakeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayNameGeneration(ReplaceUnderscores.class)
@ExtendWith(MockitoExtension.class)
class DefaultProductPriceChangeUseCaseTest {

    @Mock
    private ProductPriceChangeEventPublisher mockEventPublisher;

    private ProductNewRepository repository;

    private ProductPriceChangeUseCase useCase;

    @BeforeEach
    void setUp() {
        repository = new ProductNewFakeRepository();

        useCase = new DefaultProductPriceChangeUseCase(repository, mockEventPublisher);
    }


    @ParameterizedTest
    @NullSource
    void change_invalid_parameters_id가_null이면_예외를_발생시킨다(final UUID value) {

        // when & then
        assertThatThrownBy(() -> useCase.change(value, Fixtures.VALID_PRODUCT_PRICE))
            .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @NullSource
    void change_invalid_parameters_price가_null이면_예외를_발생시킨다(final ProductPrice value) {

        // when & then
        assertThatThrownBy(() -> useCase.change(UUID.randomUUID(), value))
            .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void change_id에_해당하는_음식이_없으면_예외를_발생시킨다() {

        // when & then
        assertThatThrownBy(() -> useCase.change(UUID.randomUUID(), Fixtures.VALID_PRODUCT_PRICE))
            .isExactlyInstanceOf(NotExistProductException.class);
    }

    @Test
    void change_id에_해당하는_음식이_없으면_가격변경_이벤트를_발행하지_않는다() {
        // given

        // when
        throwExceptionScenario();

        // then
        verify(mockEventPublisher, never())
            .publish(any());
    }

    private void throwExceptionScenario() {
        try {
            useCase.change(UUID.randomUUID(), Fixtures.VALID_PRODUCT_PRICE);
        } catch (final Exception ignored) {
        }
    }

    @Test
    void change_입력받은_가격으로_음식_가격을_변경시킨다() {
        // given
        final ProductNew product = repository.save(Fixtures.create(5_000L));

        // when
        useCase.change(product.getId(), ProductPrice.create(10_000L));

        // then
        assertThat(product.getPrice())
            .isEqualTo(ProductPrice.create(10_000L));
    }

    @Test
    void change_가격이_변경되면_가격변경_이벤트를_발행한다() {
        // given
        final ProductNew product = repository.save(Fixtures.create(5_000L));

        // when
        useCase.change(product.getId(), ProductPrice.create(10_000L));
        // then
        // verify
        verify(mockEventPublisher)
            .publish(product.getId());
    }
}