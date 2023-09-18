package kitchenpos.menu.domain.menu;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@DisplayNameGeneration(ReplaceUnderscores.class)
class MenuProductNewTest {

    @ParameterizedTest
    @CsvSource(value = {"1000,10", "10,1"})
    void amount_수량과_음식의_가격을_곱한_값을_반환한다(final int price, final int quantity) {
        // given
        final MenuProductNew menuProduct = create(price, quantity);

        // when
        final long actual = menuProduct.amount();

        // then
        assertThat(actual).isEqualTo(price * quantity);
    }

    private MenuProductNew create(final int price, final int quantity) {

        return MenuProductNew.create(UUID.randomUUID(),
            ProductPrice.create(price), Quantity.create(quantity));
    }
}