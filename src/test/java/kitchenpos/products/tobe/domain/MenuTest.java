package kitchenpos.products.tobe.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class MenuTest {
    private final Profanities profanities = new FakeProfanities();

    @Test
    void 메뉴를_생성한다() {
        final UUID id = UUID.randomUUID();
        final DisplayedName name = new DisplayedName("치킨", profanities);
        final BigDecimal price = BigDecimal.valueOf(16_000);
        final List<MenuProduct> menuProducts = Arrays.asList(
                new MenuProduct(UUID.randomUUID(), 16_000, 1)
        );

        assertDoesNotThrow(() -> new Menu(id, name, price, menuProducts));
    }

    @Test
    void 메뉴의_가격이_음수면_예외가_발생한다() {
        final UUID id = UUID.randomUUID();
        final DisplayedName name = new DisplayedName("치킨", profanities);
        final BigDecimal price = BigDecimal.valueOf(-16_000);
        final List<MenuProduct> menuProducts = Arrays.asList(
                new MenuProduct(UUID.randomUUID(), 16_000, 1)
        );
        assertThatThrownBy(() -> new Menu(id, name, price, menuProducts))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 메뉴의_가격이_메뉴_상품의_금액의_합보다_크면_예외가_발생한다() {
        final UUID id = UUID.randomUUID();
        final DisplayedName name = new DisplayedName("치킨", profanities);
        final MenuPrice price = new MenuPrice(BigDecimal.valueOf(32_000));
        final List<MenuProduct> menuProducts = Arrays.asList(
                new MenuProduct(UUID.randomUUID(), 16_000, 1)
        );
        assertThatThrownBy(() -> new Menu(id, name, price, menuProducts))
                .isInstanceOf(IllegalArgumentException.class);


    }
}
