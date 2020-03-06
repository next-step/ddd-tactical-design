package kitchenpos.menus.bo;

import kitchenpos.menus.dao.MenuDao;
import kitchenpos.menus.dao.MenuGroupDao;
import kitchenpos.menus.dao.MenuProductDao;
import kitchenpos.menus.model.Menu;
import kitchenpos.products.application.InMemoryProductDao;
import kitchenpos.products.infrastructure.dao.ProductDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static kitchenpos.menus.Fixtures.twoChickens;
import static kitchenpos.menus.Fixtures.twoFriedChickens;
import static kitchenpos.products.Fixtures.friedChicken;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertAll;

class MenuBoTest {
    private final MenuDao menuDao = new InMemoryMenuDao();
    private final MenuGroupDao menuGroupDao = new InMemoryMenuGroupDao();
    private final MenuProductDao menuProductDao = new InMemoryMenuProductDao();
    private final ProductDao productDao = new InMemoryProductDao();

    private MenuBo menuBo;

    @BeforeEach
    void setUp() {
        menuBo = new MenuBo(menuDao, menuGroupDao, menuProductDao, productDao);
        menuGroupDao.save(twoChickens());
        productDao.save(friedChicken());
    }

    @DisplayName("1 개 이상의 등록된 상품으로 메뉴를 등록할 수 있다.")
    @Test
    void create() {
        // given
        final Menu expected = twoFriedChickens();

        // when
        final Menu actual = menuBo.create(expected);

        // then
        assertMenu(expected, actual);
    }

    @DisplayName("메뉴의 가격이 올바르지 않으면 등록할 수 없다.")
    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"-1000", "33000"})
    void create(final BigDecimal price) {
        // given
        final Menu expected = twoFriedChickens();
        expected.setPrice(price);

        // when
        // then
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> menuBo.create(expected));
    }

    @DisplayName("메뉴는 특정 메뉴 그룹에 속해야 한다.")
    @ParameterizedTest
    @NullSource
    void create(final Long menuGroupId) {
        // given
        final Menu expected = twoFriedChickens();
        expected.setMenuGroupId(menuGroupId);

        // when
        // then
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> menuBo.create(expected));
    }

    @DisplayName("메뉴의 목록을 조회할 수 있다.")
    @Test
    void list() {
        // given
        final Menu twoFriedChickens = menuDao.save(twoFriedChickens());

        // when
        final List<Menu> actual = menuBo.list();

        // then
        assertThat(actual).containsExactlyInAnyOrderElementsOf(Arrays.asList(twoFriedChickens));
    }

    private void assertMenu(final Menu expected, final Menu actual) {
        assertThat(actual).isNotNull();
        assertAll(
                () -> assertThat(actual.getName()).isEqualTo(expected.getName()),
                () -> assertThat(actual.getPrice()).isEqualTo(expected.getPrice()),
                () -> assertThat(actual.getMenuGroupId()).isEqualTo(expected.getMenuGroupId()),
                () -> assertThat(actual.getMenuProducts())
                        .containsExactlyInAnyOrderElementsOf(expected.getMenuProducts())
        );
    }
}
