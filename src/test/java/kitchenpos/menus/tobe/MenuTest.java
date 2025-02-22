package kitchenpos.menus.tobe;

import kitchenpos.menus.tobe.exception.InvalidMenuNameException;
import kitchenpos.menus.tobe.exception.InvalidMenuPricePeriodException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class MenuTest {

    private MenuGroup menuGroup;
    private List<MenuProduct> menuProducts;

    @BeforeEach
    void setUp() {
        menuGroup = new MenuGroup(UUID.randomUUID(), "메뉴 그룹");
        menuProducts = new ArrayList<>(
                List.of(
                        new MenuProduct(1L, 1000L, 1L, 1L),
                        new MenuProduct(2L, 2000L, 2L, 2L)
                )
        );
    }

    @DisplayName("메뉴의 가격은 0원 이상이어야 한다.")
    @ValueSource(longs = {-1, -1000, -100000})
    @ParameterizedTest(name = "{index}. 메뉴 가격 : `{0}`")
    void createWithNegativePrice(final long price) {
        assertThatThrownBy(
                () -> new Menu(null, "메뉴", price, menuGroup, menuProducts, true)
        ).isExactlyInstanceOf(InvalidMenuPricePeriodException.class);
    }

    @DisplayName("메뉴의 이름이 없거나 비어있으면 생성할 수 없다.")
    @NullAndEmptySource
    @ParameterizedTest(name = "{index}. 메뉴 이름 : `{0}`")
    void createWithInvalidName(final String name) {
        assertThatThrownBy(
                () -> new Menu(null, name, 1000L, menuGroup, menuProducts, true)
        ).isExactlyInstanceOf(InvalidMenuNameException.class);
    }

    @DisplayName("메뉴는 메뉴 그룹이 있어야 한다.")
    @NullSource
    void createWithNullMenuGroup(final MenuGroup menuGroup) {
        assertThatThrownBy(
                () -> new Menu(null, "메뉴", 1000L, menuGroup, menuProducts, true)
        ).isExactlyInstanceOf(IllegalArgumentException.class);
    }
}
