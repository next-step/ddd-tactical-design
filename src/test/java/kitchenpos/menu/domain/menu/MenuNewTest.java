package kitchenpos.menu.domain.menu;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import java.util.UUID;
import kitchenpos.menu.Fixtures;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(ReplaceUnderscores.class)
class MenuNewTest {

    @Test
    void menuPrice는_menuProducts의_금액의_합보다_작거나_같아야_한다() {
        // given
        final MenuProductNew menuProduct1 = create(1_000, 2);
        final MenuProductNew menuProduct2 = create(3_000, 1);

        // when & then
        assertThatThrownBy(() -> MenuNew.create(Fixtures.TEST_MENU_NAME, MenuPrice.create(5_100),
            MenuProducts.create(List.of(menuProduct1, menuProduct2)), Fixtures.TEST_MENU_GROUP))
            .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    private MenuProductNew create(final int price, final int quantity) {

        return MenuProductNew.create(UUID.randomUUID(),
            ProductPrice.create(price), Quantity.create(quantity));
    }
}