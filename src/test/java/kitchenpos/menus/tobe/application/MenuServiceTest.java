package kitchenpos.menus.tobe.application;

import kitchenpos.menus.tobe.domain.Menu;
import kitchenpos.menus.tobe.domain.MenuBuilder;
import kitchenpos.menus.tobe.domain.MenuPrice;
import kitchenpos.menus.tobe.domain.repository.MenuRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static kitchenpos.menus.tobe.Fixtures.twoFriedChickens;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class MenuServiceTest {
    @Mock
    private MenuRepository menuRepository;
    @Mock
    private MenuGroupService menuGroupService;
    @Mock
    private MenuProductService menuProductService;

    @InjectMocks
    private MenuService menuService;

    @DisplayName("1 개 이상의 등록된 상품으로 메뉴를 등록할 수 있다.")
    @Test
    void create() {
        // given
        final Menu expected = twoFriedChickens();
        final BigDecimal expectedTotalProductsPrice = expected.getPrice().getValue().add(BigDecimal.TEN);

        given(menuProductService.getTotalMenuProductsPrice(expected.getMenuProducts())).willReturn(expectedTotalProductsPrice);
        given(menuRepository.save(expected)).willReturn(expected);

        // when
        final Menu actual = menuService.create(expected);

        // then
        assertMenu(expected, actual);
    }

    @DisplayName("메뉴의 가격이 0보다 작으면 등록할 수 없다.")
    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"-1000"})
    void create1(final BigDecimal price) {
        // given
        // when
        // then
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> MenuBuilder.aMenu()
                .withId(twoFriedChickens().getId())
                .withName(twoFriedChickens().getName())
                .withMenuGroupId(twoFriedChickens().getMenuGroupId())
                .withMenuProducts(twoFriedChickens().getMenuProducts())
                .withMenuPrice(new MenuPrice(price))
                .build());
    }

    @DisplayName("메뉴의 가격이 올바르지 않으면 등록할 수 없다.")
    @ParameterizedTest
    @ValueSource(strings = {"33000"})
    void create2(final BigDecimal price) {
        // given
        Menu expected = MenuBuilder.aMenu()
                .withId(twoFriedChickens().getId())
                .withName(twoFriedChickens().getName())
                .withMenuGroupId(twoFriedChickens().getMenuGroupId())
                .withMenuProducts(twoFriedChickens().getMenuProducts())
                .withMenuPrice(new MenuPrice(price))
                .build();
        BigDecimal expectedTotalProductsPrice = price.subtract(BigDecimal.valueOf(10_000L));

        // when
        given(menuProductService.getTotalMenuProductsPrice(expected.getMenuProducts())).willReturn(expectedTotalProductsPrice);

        // then
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> menuService.create(expected));
    }

    @DisplayName("메뉴는 특정 메뉴 그룹에 속해야 한다.")
    @ParameterizedTest
    @NullSource
    void create(final Long menuGroupId) {
        // given
        // when
        // then
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> MenuBuilder.aMenu()
                .withId(twoFriedChickens().getId())
                .withName(twoFriedChickens().getName())
                .withMenuGroupId(menuGroupId)
                .withMenuProducts(twoFriedChickens().getMenuProducts())
                .withMenuPrice(twoFriedChickens().getPrice())
                .build());
    }

    @DisplayName("메뉴의 목록을 조회할 수 있다.")
    @Test
    void list() {
        // given
        final Menu expected = twoFriedChickens();
        given(menuRepository.findAll()).willReturn(Arrays.asList(expected));

        // when
        final List<Menu> actual = menuService.list();

        // then
        assertThat(actual).containsExactlyInAnyOrderElementsOf(Arrays.asList(expected));
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
