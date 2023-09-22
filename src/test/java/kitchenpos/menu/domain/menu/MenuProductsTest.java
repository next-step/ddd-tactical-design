package kitchenpos.menu.domain.menu;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(ReplaceUnderscores.class)
class MenuProductsTest {

    @Test
    void totalAmount_가지고_있는_menuProduct의_수량과_가격을_곱한_결과의_총_합을_반환한다() {
        // given
        final MenuProducts menuProducts = MenuProducts.create(List.of(
            create(1_000, 10),
            create(3_000, 1)
        ));

        // when
        final long actual = menuProducts.totalAmount();

        // then
        assertThat(actual).isEqualTo(13_000);
    }

    private MenuProductNew create(final int price, final int quantity) {

        return MenuProductNew.create(UUID.randomUUID(),
            ProductPrice.create(price), Quantity.create(quantity));
    }
}