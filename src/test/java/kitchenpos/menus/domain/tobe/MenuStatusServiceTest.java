package kitchenpos.menus.domain.tobe;

import kitchenpos.menus.dto.MenuRequest;
import kitchenpos.menus.dto.ProductResponse;
import kitchenpos.support.StubBanWordFilter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static kitchenpos.support.MenuGenerator.createMenuProduct;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class MenuStatusServiceTest {
    private MenuStatusService menuStatusService;

    private ProductResponse productResponse;

    @Mock
    private MenuRepository menuRepository;

    private List<MenuProduct> menuProducts;

    private MenuRequest menuRequest;

    @BeforeEach
    void setUp() {
        menuStatusService = new MenuStatusService(menuRepository);
        menuProducts = Arrays.asList(
                createMenuProduct(1L, 1l),
                createMenuProduct(2L, 1l));
    }

    @Test
    void show() {
        final Menu menu = new Menu(
                "반반치킨", new StubBanWordFilter(false), new MenuPrice(BigDecimal.valueOf(10_000)),
                UUID.randomUUID(), false, menuProducts
        );
        given(menuRepository.findByUUId(menu.getId())).willReturn(Optional.of(menu));
        productResponse = new ProductResponse(null, BigDecimal.valueOf(16_000));

        menuStatusService.showMenu(menu, productResponse);

        assertThat(menu.getDisplayed()).isTrue();
    }

    @DisplayName("메뉴를 보이지 않게 할 수 있다.")
    @Test
    void hide() {
        final Menu menu = new Menu(
                "반반치킨", new StubBanWordFilter(false), new MenuPrice(BigDecimal.valueOf(10_000)),
                UUID.randomUUID(), false, menuProducts
        );
        given(menuRepository.findByUUId(menu.getId())).willReturn(Optional.of(menu));

        menuStatusService.hideMenu(menu);

        assertThat(menu.getDisplayed()).isFalse();
    }

    @DisplayName("메뉴의 가격을 변경할 수 있다.")
    @Test
    void change_price() {
        final Menu menu = new Menu(
                "반반치킨", new StubBanWordFilter(false), new MenuPrice(BigDecimal.valueOf(10_000)),
                UUID.randomUUID(), false, menuProducts
        );
        given(menuRepository.findByUUId(menu.getId())).willReturn(Optional.of(menu));
        productResponse = new ProductResponse(null, BigDecimal.valueOf(16_000));
        final BigDecimal newPrice = BigDecimal.valueOf(12_000);
        menuRequest = new MenuRequest(null, newPrice, null, null, null);

        menuStatusService.changePrice(menu.getId(), productResponse, menuRequest);

        assertThat(menu.getPrice()).isEqualTo(new MenuPrice(newPrice));
    }

    @DisplayName("Menu의 가격 변경시 MenuProducts의 금액의 합보다 적거나 같아야 한다.")
    @Test
    void change_price_with_bigger_than_menu_products_sum() {
        final Menu menu = new Menu(
                "반반치킨", new StubBanWordFilter(false), new MenuPrice(BigDecimal.valueOf(10_000)),
                UUID.randomUUID(), false, menuProducts
        );
        given(menuRepository.findByUUId(menu.getId())).willReturn(Optional.of(menu));
        productResponse = new ProductResponse(null, BigDecimal.valueOf(16_000));
        final BigDecimal newPrice = BigDecimal.valueOf(20_000);
        menuRequest = new MenuRequest(null, newPrice, null, null, null);

        assertThatCode(() -> menuStatusService.changePrice(menu.getId(), productResponse, menuRequest));
    }
}
