package kitchenpos.product.adapter.out;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import java.util.UUID;
import kitchenpos.menu.adapter.in.MenuDisplayingRearranger;
import kitchenpos.product.application.port.out.ProductPriceChangeEventPublisher;
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
class DefaultProductPriceChangeEventPublisherTest {

    @Mock
    private MenuDisplayingRearranger mockRearranger;

    private ProductPriceChangeEventPublisher publisher;

    @BeforeEach
    void setUp() {
        publisher = new DefaultProductPriceChangeEventPublisher(mockRearranger);
    }

    @ParameterizedTest
    @NullSource
    void publish_invalid_parameters_id가_null이면_예외를_발생시킨다(final UUID value) {

        // when & then
        assertThatThrownBy(() -> publisher.publish(value))
            .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @NullSource
    void publish_invalid_parameters_id가_null이면_rearranger는_실행되지_않는다(final UUID value) {

        // when
        try {
            publisher.publish(value);
        } catch (final Exception ignored) {
        }

        // then
        // verify
        verify(mockRearranger, never())
            .execute(any());
    }

    @Test
    void publish_id를_인자로_rerranger가_실행된다() {
        // given
        final UUID id = UUID.randomUUID();

        // when
        publisher.publish(id);

        // then
        // verify
        verify(mockRearranger)
            .execute(id);
    }
}